package ar.edu.unlam.cripto.grain;

public class KeystreamGenerator {

	private short[] key;
	private short[] seed;
	private short[] lfsr;
	private short[] nfsr;

	private static final Integer KEY_LONG = 80;
	private static final Integer SEED_LONG = 64;

	public KeystreamGenerator(short[] key, short[] seed) throws Exception {

		if (key.length == KEY_LONG) {
			this.key = new short[KEY_LONG];
			for (int i = 0; i < this.key.length; i++) {
				this.key[i] = key[i];
			}

		} else {
			throw new Exception("Longitud de la clave: " + key.length + ". Longitud requerida: " + KEY_LONG + " bits.");
		}

		if (seed.length == SEED_LONG) {
			this.seed = new short[SEED_LONG];
			for (int i = 0; i < this.seed.length; i++) {
				this.seed[i] = seed[i];
			}
		} else {
			throw new Exception(
					"Longitud de la semilla: " + seed.length + ". Longitud requerida: " + SEED_LONG + " bits.");
		}

		this.lfsr = new short[KEY_LONG];
		this.nfsr = new short[KEY_LONG];
		init();
	}

	private void init() {
		int i;

		for (i = 0; i < key.length; i++) {
			nfsr[i] = key[i];
		}

		for (i = 0; i < seed.length; i++) {
			lfsr[i] = seed[i];
		}

		for (; i < lfsr.length; i++) {
			lfsr[i] = 1;
		}

		for (i = 0; i < 160; i++) {
			startClock();
		}

	}

	public short[] generarKeystream(int bytes) {
		short[] keystream = new short[bytes * 8];

		for (int i = 0; i < keystream.length; i++) {
			keystream[i] = clock();
		}

		return keystream;
	}

	private short clock() {
		short output = output();
		shiftLeftNFSR(feedbackNFSR());
		shiftLeftLFSR(feedbackLFSR());
		return output;
	}

	private void startClock() {
		short output = output();
		shiftLeftNFSR((short) ((feedbackNFSR() + output) % 2));
		shiftLeftLFSR((short) ((feedbackLFSR() + output) % 2));
	}

	private short feedbackLFSR() {
		return (short) ((lfsr[0] + lfsr[13] + lfsr[23] + lfsr[38] + lfsr[51] + lfsr[62]) % 2);
	}

	private void shiftLeftLFSR(short bit) {
		for (int i = 0; i < lfsr.length - 1; i++) {
			lfsr[i] = lfsr[i + 1];
		}
		lfsr[lfsr.length - 1] = bit;
	}

	private short feedbackNFSR() {
		return (short) ((lfsr[0] + nfsr[62] + nfsr[60] + nfsr[52] + nfsr[45] + nfsr[37] + nfsr[33] + nfsr[28] + nfsr[21]
				+ nfsr[14] + nfsr[9] + nfsr[0] + nfsr[63] * nfsr[60] + nfsr[37] * nfsr[33] + nfsr[15] * nfsr[9]
				+ nfsr[60] * nfsr[52] * nfsr[45] + nfsr[33] * nfsr[28] * nfsr[21]
				+ nfsr[63] * nfsr[45] * nfsr[28] * nfsr[9] + nfsr[60] * nfsr[52] * nfsr[37] * nfsr[33]
				+ nfsr[63] * nfsr[60] + nfsr[63] * nfsr[60] * nfsr[52] * nfsr[45] * nfsr[37]
				+ nfsr[33] * nfsr[28] * nfsr[21] * nfsr[15] * nfsr[9]
				+ nfsr[52] * nfsr[45] * nfsr[37] * nfsr[33] * nfsr[28] * nfsr[21]) % 2);
	}

	private void shiftLeftNFSR(short bit) {
		for (int i = 0; i < nfsr.length - 1; i++) {
			nfsr[i] = nfsr[i + 1];
		}
		nfsr[nfsr.length - 1] = bit;
	}

	private short output() {
		short bi = (short) (nfsr[1] + nfsr[2] + nfsr[4] + nfsr[10] + nfsr[31] + nfsr[43] + nfsr[56]);
		short h = filter();
		return (short) ((bi + h) % 2);
	}

	private short filter() {
		return (short) (lfsr[25] + nfsr[63] + lfsr[3] * lfsr[64] + lfsr[46] * lfsr[64] + lfsr[64] * nfsr[63]
				+ lfsr[3] * lfsr[25] * lfsr[46] + lfsr[3] * lfsr[46] * lfsr[64] + lfsr[3] * lfsr[46] * nfsr[63]
				+ lfsr[25] * lfsr[46] * nfsr[63] + lfsr[46] * lfsr[64] * nfsr[63]);
	}

}
