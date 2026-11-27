package me.blog.hgl1002.openwnn.layout;
import android.util.SparseArray;
import static me.blog.hgl1002.openwnn.hangul.Abbreviation.appendAbbreviation;

public class LayoutMoachigiSebul {

	public static final int[][] JAMO_SEBUL_AHNMATAE = {
			{49,0x31,0x21},
			{50,0x32,0x40},
			{51,0x33,0x23},
			{52,0x34,0x24},
			{53,0x35,0x25},
			{54,0x36,0x5e},
			{55,0x37,0x26},
			{56,0x38,0x2a},
			{57,0x39,0x28},
			{48,0x30,0x29},


			{113, 0x1106, 0x1106},		// q
			{119, 0x1109, 0x1140},		// w
			{101, 0x1102, 0x1102},		// e
			{114, 0x1105, 0x1105},		// r
			{116, 0x1112, 0x1159},		// t
			{121, 0x1167, 0x3b},		// y
			{117, 0x1163, 0x27},		// u
			{105, 0x1173, 0x2f},		// i
			{111, 0x116d, 0x5b},		// o
			{112, 0x1172, 0x5d},		// p
			{91, 0x2c, 0x7b},
			{93, 0x3f, 0x7d},

			{97, 0x1107, 0x1111},		// a
			{115, 0x110c, 0x110e},		// s
			{100, 0x1103, 0x1110},		// d
			{102, 0x1100, 0x110f},		// f
			{103, 0x110b, 0x114c},		// g
			{104, 0x1165, 0x1165},		// h
			{106, 0x1161, 0x119e},		// j
			{107, 0x1175, 0x1175},		// k
			{108, 0x1169, 0x1169},		// l
			{59, 0x116e, 0x3a},
			{39, 0x2e, 0x22},

			{122, 0x11bd, 0x11be},		// z
			{120, 0x11ae, 0x11c0},		// x
			{99, 0x11b8, 0x11c1},		// c
			{118, 0x11a8, 0x11bf},		// v
			{98, 0x11bc, 0x11f0},		// b
			{110, 0x11ba, 0x11eb},		// n
			{109, 0x11ab, 0x11ab},		// m
			{44, 0x11b7, 0x3c},
			{46, 0x11af, 0x3e},
			{47, 0x11c2, 0x11f9},

			{128, 0x2e, 0x2c},
	};

	public static final int[][] COMB_SEBUL_AHNMATAE = {

			{0x1100, 0x1103, 0x1104}, // ㄱ + ㄷ = ㄸ
			{0x1100, 0x110b, 0x1101}, // ㄱ + ㅇ = ㄲ
			{0x1100, 0x1112, 0x110f}, // ㄱ + ㅎ = ㅋ
			{0x1102, 0x1109, 0x110a}, // ㄴ + ㅅ = ㅆ
			{0x1103, 0x1100, 0x1104}, // ㄷ + ㄱ = ㄸ
			{0x1103, 0x110c, 0x110d}, // ㄷ + ㅈ = ㅉ
			{0x1103, 0x1112, 0x1110}, // ㄷ + ㅎ = ㅌ
			{0x1107, 0x110c, 0x1108}, // ㅂ + ㅈ = ㅃ
			{0x1107, 0x1112, 0x1111}, // ㅂ + ㅎ = ㅍ
			{0x1109, 0x1102, 0x110a}, // ㅅ + ㄴ = ㅆ
			{0x110b, 0x1100, 0x1101}, // ㅇ + ㄱ = ㄲ
			{0x110c, 0x1103, 0x110d}, // ㅈ + ㄷ = ㅉ
			{0x110c, 0x1107, 0x1108}, // ㅈ + ㅂ = ㅃ
			{0x110c, 0x1112, 0x110e}, // ㅈ + ㅎ = ㅊ
			{0x1112, 0x1100, 0x110f}, // ㅎ + ㄱ = ㅋ
			{0x1112, 0x1103, 0x1110}, // ㅎ + ㄷ = ㅌ
			{0x1112, 0x1107, 0x1111}, // ㅎ + ㅂ = ㅍ
			{0x1112, 0x110c, 0x110e}, // ㅎ + ㅈ + ㅊ

			{0x1161, 0x1169, 0x116a}, // ㅏ + ㅗ = ㅘ
			{0x1161, 0x1175, 0x1162}, // ㅏ + ㅣ = ㅐ
			{0x1162, 0x1169, 0x116b}, // ㅐ + ㅗ = ㅙ
			{0x1163, 0x1175, 0x1164}, // ㅑ + ㅣ = ㅒ
			{0x1165, 0x116e, 0x116f}, // ㅓ + ㅜ = ㅝ
			{0x1165, 0x1175, 0x1166}, // ㅓ + ㅣ = ㅔ
			{0x1166, 0x116e, 0x1170}, // ㅔ + ㅜ = ㅞ
			{0x1167, 0x1175, 0x1168}, // ㅕ + ㅣ = ㅖ
			{0x1169, 0x1161, 0x116a}, // ㅗ + ㅏ = ㅘ
			{0x1169, 0x1175, 0x116c}, // ㅗ + ㅣ = ㅚ
			{0x116a, 0x1175, 0x116b}, // ㅘ + ㅣ = ㅙ
			{0x116c, 0x1161, 0x116b}, // ㅚ + ㅏ = ㅙ
			{0x116e, 0x1165, 0x116f}, // ㅜ + ㅓ = ㅝ
			{0x116e, 0x1175, 0x1171}, // ㅜ + ㅣ = ㅟ
			{0x116f, 0x1175, 0x1170}, // ㅝ + ㅣ = ㅞ
			{0x1171, 0x1165, 0x1170}, // ㅟ + ㅓ = ㅞ
			{0x1173, 0x1175, 0x1174}, // ㅡ + ㅣ = ㅢ
			{0x1175, 0x1161, 0x1162}, // ㅣ + ㅏ = ㅐ
			{0x1175, 0x1163, 0x1164}, // ㅣ + ㅑ = ㅒ
			{0x1175, 0x1165, 0x1166}, // ㅣ + ㅓ = ㅔ
			{0x1175, 0x1167, 0x1168}, // ㅣ + ㅕ = ㅖ
			{0x1175, 0x1169, 0x116c}, // ㅣ + ㅗ = ㅚ
			{0x1175, 0x116e, 0x1171}, // ㅣ + ㅜ = ㅟ
			{0x1175, 0x1173, 0x1174}, // ㅣ + ㅡ = ㅢ
			{0x119e, 0x1175, 0x11a1}, // ㆍ + ㅣ = ㆎ
			{0x119e, 0x119e, 0x11a2}, // ㆍ + ㆍ = ᆢ

			{0x11a8, 0x11af, 0x11b0}, // ㄱ + ㄹ = ㄺ
			{0x11a8, 0x11ba, 0x11aa}, // ㄱ + ㅅ = ㄳ
			{0x11a8, 0x11bc, 0x11a9}, // ㄱ + ㅇ = ㄲ
			{0x11a8, 0x11c2, 0x11bf}, // ㄱ + ㅎ = ㅋ
			{0x11ab, 0x11ba, 0x11bb}, // ㄴ + ㅅ = ㅆ
			{0x11ab, 0x11bd, 0x11ac}, // ㄴ + ㅈ = ㄵ
			{0x11ab, 0x11c2, 0x11ad}, // ㄴ + ㅎ = ㄶ
			{0x11ae, 0x11af, 0x11ce}, // ㄷ + ㄹ = ᇎ
			{0x11ae, 0x11c2, 0x11c0}, // ㄷ + ㅎ = ㅌ
			{0x11af, 0x11a8, 0x11b0}, // ㄹ + ㄱ = ㄺ
			{0x11af, 0x11ae, 0x11ce}, // ㄹ + ㄷ = ᇎ
			{0x11af, 0x11b7, 0x11b1}, // ㄹ + ㅁ = ㄻ
			{0x11af, 0x11b8, 0x11b2}, // ㄹ + ㅂ = ㄼ
			{0x11af, 0x11ba, 0x11b3}, // ㄹ + ㅅ = ㄽ
			{0x11af, 0x11c2, 0x11b6}, // ㄹ + ㅎ = ㅀ
			{0x11b2, 0x11c2, 0x11b5}, // ㄼ + ㅎ = ㄿ
			{0x11b6, 0x11ae, 0x11b4}, // ㅀ + ㄷ = ㄾ
			{0x11b6, 0x11b8, 0x11b5}, // ㅀ + ㅂ = ㄿ
			{0x11b7, 0x11af, 0x11b1}, // ㅁ + ㄹ = ㄻ
			{0x11b8, 0x11af, 0x11b2}, // ㅂ + ㄹ = ㄼ
			{0x11b8, 0x11ba, 0x11b9}, // ㅂ + ㅅ = ㅄ
			{0x11b8, 0x11c2, 0x11c1}, // ㅂ + ㅎ = ㅍ
			{0x11ba, 0x11a8, 0x11aa}, // ㅅ + ㄱ = ㄳ
			{0x11ba, 0x11ab, 0x11bb}, // ㅅ + ㄴ = ㅆ
			{0x11ba, 0x11af, 0x11b3}, // ㅅ + ㄹ = ㄽ
			{0x11ba, 0x11b8, 0x11b9}, // ㅅ + ㅂ = ㅄ
			{0x11bc, 0x11a8, 0x11a9}, // ㅇ + ㄱ = ㄲ
			{0x11bd, 0x11ab, 0x11ac}, // ㅈ + ㄴ = ㄵ
			{0x11bd, 0x11c2, 0x11be}, // ㅈ + ㅎ = ㅊ
			{0x11c0, 0x11af, 0x11b4}, // ㅌ + ㄹ = ㄾ
			{0x11c1, 0x11af, 0x11b5}, // ㅍ + ㄹ = ㄿ
			{0x11c2, 0x11a8, 0x11bf}, // ㅎ + ㄱ = ㅋ
			{0x11c2, 0x11ab, 0x11ad}, // ㅎ + ㄴ = ㄶ
			{0x11c2, 0x11ae, 0x11c0}, // ㅎ + ㄷ = ㅌ
			{0x11c2, 0x11af, 0x11b6}, // ㅎ + ㄹ = ㅀ
			{0x11c2, 0x11b8, 0x11c1}, // ㅎ + ㅂ = ㅍ
			{0x11c2, 0x11bd, 0x11be}, // ㅎ + ㅈ = ㅊ
			{0x11ce, 0x11c2, 0x11b4}, // ᇎ + ㅎ = ㄾ

			{0x1100, 0x1100, 0x1101},	// ㄲ
			{0x1103, 0x1103, 0x1104},	// ㄸ
			{0x1107, 0x1107, 0x1108},	// ㅃ
			{0x1109, 0x1109, 0x110a},	// ㅆ
			{0x110c, 0x110c, 0x110d},	// ㅉ

			{0x1169, 0x1161, 0x116a},	// ㅘ
			{0x1169, 0x1162, 0x116b},	// ㅙ
			{0x1169, 0x1175, 0x116c},	// ㅚ
			{0x116e, 0x1165, 0x116f},	// ㅝ
			{0x116e, 0x1166, 0x1170},	// ㅞ
			{0x116e, 0x1175, 0x1171},	// ㅟ
			{0x1173, 0x1175, 0x1174},	// ㅢ
			{0x119e, 0x1175, 0x11a1},	// ㆎ
			{0x119e, 0x119e, 0x11a2},	// ᆢ

			{0x11a8, 0x11a8, 0x11a9},	// ㄲ
			{0x11a8, 0x11ba, 0x11aa},	// ㄳ
			{0x11ab, 0x11bd, 0x11ac},	// ㄵ
			{0x11ab, 0x11c2, 0x11ad},	// ㄶ
			{0x11af, 0x11a8, 0x11b0},	// ㄺ
			{0x11af, 0x11b7, 0x11b1},	// ㄻ
			{0x11af, 0x11b8, 0x11b2},	// ㄼ
			{0x11af, 0x11ba, 0x11b3},	// ㄽ
			{0x11af, 0x11c0, 0x11b4},	// ㄾ
			{0x11af, 0x11c1, 0x11b5},	// ㄿ
			{0x11af, 0x11c2, 0x11b6},	// ㅀ
			{0x11b8, 0x11ba, 0x11b9},	// ㅄ
			{0x11ba, 0x11ba, 0x11bb},	// ㅆ
	};

	public static final int[][] JAMO_SEBUL_SEMOE = {

			{49,0x31,0x21},
			{50,0x32,0x40},
			{51,0x33,0x23},
			{52,0x34,0x24},
			{53,0x35,0x25},
			{54,0x36,0x5e},
			{55,0x37,0x26},
			{56,0x38,0x2a},
			{57,0x39,0x28},
			{48,0x30,0x29},

			{113, 0x11ba, 0x11be},		// q
			{119, 0x11b8, 0x11c1},		// w
			{101, 0x11af, 0x11bd},		// e
			{114, 0x1165, 0x116f},		// r
			{116, 0x1167, 0x1164},		// t
			{121, 0x1106, 0x3008},		// y
			{117, 0x1102, 0x3009},		// u
			{105, 0x1103, 0x1110},		// i
			{111, 0x1107, 0x1111},		// o
			{112, 0x2e, 0x3b},		    // p

			{97, 0x11bc, 0x11c0},		// a
			{115, 0x11ab, 0x11c2},		// s
			{100, 0x1175, 0x1175},		// d
			{102, 0x1161, 0x119e},		// f
			{103, 0x1173, 0x1163},		// g
			{104, 0x1112, 0xb7},		// h
			{106, 0x110b, 0x110b},		// j
			{107, 0x1100, 0x110f},		// k
			{108, 0x110c, 0x110e},		// l
			{59, 0x11bb, 0x3a},

			{122, 0x11b7, 0x11ae},		// z
			{120, 0x11a8, 0x11bf},		// x
			{99, 0x1166, 0x1168},		// c
			{118, 0x1169, 0x116d},		// v
			{98, 0x116e, 0x1172},		// b
			{110, 0x1109, 0x1109},		// n
			{109, 0x1105, 0xb7},		// m
			{44, 0x2c, 0x3c},
			{46, 0x011169, 0x3e},

			{128, 0x2e, 0x2c},
	};


	public static final int[][] COMB_SEBUL_SEMOE = {

			{0x1100, 0x1100, 0x1101}, // ㄱ + ㄱ = ㄲ
			{0x1100, 0x1102, 0x1129}, // ㄱ + ㄴ = ᄩ
			{0x1100, 0x1103, 0x1117}, // ㄱ + ㄷ = ᄗ
			{0x1100, 0x1105, 0xa964}, // ㄱ + ㄹ = ꥤ
			{0x1100, 0x1107, 0x111e}, // ㄱ + ㅂ = ᄞ
			{0x1100, 0x1109, 0x112d}, // ㄱ + ㅅ = ᄭ
			{0x1100, 0x110b, 0x1101}, // ㄱ + ㅇ = ㄲ
			{0x1100, 0x110c, 0x1152}, // ㄱ + ㅈ = ᅒ
			{0x1100, 0x1112, 0x110f}, // ㄱ + ㅎ = ㅋ
			{0x1100, 0x1121, 0x1122}, // ㄱ + ᄡ = ᄢ
			{0x1100, 0x1147, 0x1141}, // ㄱ + ᅇ = ᅁ
			{0x1101, 0x1100, 0x110f}, // ㄲ + ㄱ = ㅋ
			{0x1102, 0x1100, 0x1129}, // ㄴ + ㄱ = ᄩ
			{0x1102, 0x1103, 0x1115}, // ㄴ + ㄷ = ᄕ
			{0x1102, 0x1105, 0x111b}, // ㄴ + ㄹ = ᄛ
			{0x1102, 0x1106, 0x111d}, // ㄴ + ㅁ = ᄝ
			{0x1102, 0x1107, 0x112b}, // ㄴ + ㅂ = ᄫ
			{0x1102, 0x1108, 0x112c}, // ㄴ + ㅃ = ᄬ
			{0x1102, 0x1109, 0x112e}, // ㄴ + ㅅ = ᄮ
			{0x1102, 0x110b, 0x114c}, // ㄴ + ㅇ = ᅌ
			{0x1102, 0x110c, 0x1157}, // ㄴ + ㅈ = ᅗ
			{0x1102, 0x1112, 0x1159}, // ㄴ + ㅎ = ᅙ
			{0x1102, 0x1121, 0x114b}, // ㄴ + ᄡ = ᅋ
			{0x1102, 0x1140, 0xa976}, // ㄴ + ᅀ = ꥶ
			{0x1102, 0x1147, 0x1114}, // ㄴ + ᅇ = ᄔ
			{0x1103, 0x1100, 0x1117}, // ㄷ + ㄱ = ᄗ
			{0x1103, 0x1102, 0x1115}, // ㄷ + ㄴ = ᄕ
			{0x1103, 0x1103, 0x1104}, // ㄷ + ㄷ = ㄸ
			{0x1103, 0x1105, 0x115e}, // ㄷ + ㄹ = ᅞ
			{0x1103, 0x1106, 0xa970}, // ㄷ + ㅁ = ꥰ
			{0x1103, 0x1107, 0x1120}, // ㄷ + ㅂ = ᄠ
			{0x1103, 0x1109, 0xa962}, // ㄷ + ㅅ = ꥢ
			{0x1103, 0x110b, 0x1104}, // ㄷ + ㅇ = ㄸ
			{0x1103, 0x110c, 0xa963}, // ㄷ + ㅈ = ꥣ
			{0x1103, 0x1112, 0x1110}, // ㄷ + ㅎ = ㅌ
			{0x1103, 0x1121, 0x1123}, // ㄷ + ᄡ = ᄣ
			{0x1103, 0x1147, 0x1142}, // ㄷ + ᅇ = ᅂ
			{0x1104, 0x1103, 0x1110}, // ㄸ + ㄷ = ㅌ
			{0x1105, 0x1100, 0xa964}, // ㄹ + ㄱ = ꥤ
			{0x1105, 0x1102, 0x111b}, // ㄹ + ㄴ = ᄛ
			{0x1105, 0x1103, 0x115e}, // ㄹ + ㄷ = ᅞ
			{0x1105, 0x1106, 0xa968}, // ㄹ + ㅁ = ꥨ
			{0x1105, 0x1107, 0xa969}, // ㄹ + ㅂ = ꥩ
			{0x1105, 0x1109, 0x112f}, // ㄹ + ㅅ = ᄯ
			{0x1105, 0x110b, 0xa976}, // ㄹ + ㅇ = ꥶ
			{0x1105, 0x110c, 0xa96d}, // ㄹ + ㅈ = ꥭ
			{0x1105, 0x1112, 0x111a}, // ㄹ + ㅎ = ᄚ
			{0x1105, 0x1147, 0x1119}, // ㄹ + ᅇ = ᄙ
			{0x1106, 0x1102, 0x111d}, // ㅁ + ㄴ = ᄝ
			{0x1106, 0x1103, 0xa970}, // ㅁ + ㄷ = ꥰ
			{0x1106, 0x1105, 0xa968}, // ㅁ + ㄹ = ꥨ
			{0x1106, 0x1107, 0x111c}, // ㅁ + ㅂ = ᄜ
			{0x1106, 0x1109, 0x1131}, // ㅁ + ㅅ = ᄱ
			{0x1106, 0x110c, 0xa971}, // ㅁ + ㅈ = ꥱ
			{0x1106, 0x1112, 0x1143}, // ㅁ + ㅎ = ᅃ
			{0x1107, 0x1100, 0x111e}, // ㅂ + ㄱ = ᄞ
			{0x1107, 0x1102, 0x112b}, // ㅂ + ㄴ = ᄫ
			{0x1107, 0x1103, 0x1120}, // ㅂ + ㄷ = ᄠ
			{0x1107, 0x1105, 0xa969}, // ㅂ + ㄹ = ꥩ
			{0x1107, 0x1106, 0x111c}, // ㅂ + ㅁ = ᄜ
			{0x1107, 0x1107, 0x1108}, // ㅂ + ㅂ = ㅃ
			{0x1107, 0x1109, 0x1121}, // ㅂ + ㅅ = ᄡ
			{0x1107, 0x110b, 0x1108}, // ㅂ + ㅇ = ㅃ
			{0x1107, 0x110c, 0x1127}, // ㅂ + ㅈ = ᄧ
			{0x1107, 0x1112, 0x1111}, // ㅂ + ㅎ = ㅍ
			{0x1107, 0x1147, 0x1144}, // ㅂ + ᅇ = ᅄ
			{0x1107, 0x114c, 0x112c}, // ㅂ + ᅌ = ᄬ
			{0x1108, 0x1102, 0x112c}, // ㅃ + ㄴ = ᄬ
			{0x1108, 0x1107, 0x1111}, // ㅃ + ㅂ = ㅍ
			{0x1109, 0x1100, 0x112d}, // ㅅ + ㄱ = ᄭ
			{0x1109, 0x1102, 0x112e}, // ㅅ + ㄴ = ᄮ
			{0x1109, 0x1103, 0xa962}, // ㅅ + ㄷ = ꥢ
			{0x1109, 0x1105, 0x112f}, // ㅅ + ㄹ = ᄯ
			{0x1109, 0x1106, 0x1131}, // ㅅ + ㅁ = ᄱ
			{0x1109, 0x1107, 0x1121}, // ㅅ + ㅂ = ᄡ
			{0x1109, 0x1109, 0x110a}, // ㅅ + ㅅ = ㅆ
			{0x1109, 0x110b, 0x110a}, // ㅅ + ㅇ = ㅆ
			{0x1109, 0x110c, 0x1136}, // ㅅ + ㅈ = ᄶ
			{0x1109, 0x1112, 0x1132}, // ㅅ + ㅎ = ᄲ
			{0x1109, 0x1121, 0x113e}, // ㅅ + ᄡ = ᄾ
			{0x1109, 0x1140, 0x113c}, // ㅅ + ᅀ = ᄼ
			{0x1109, 0x1147, 0x1145}, // ㅅ + ᅇ = ᅅ
			{0x1109, 0x1154, 0x113d}, // ㅅ + ᅔ = ᄽ
			{0x1109, 0x1155, 0x113f}, // ㅅ + ᅕ = ᄿ
			{0x110b, 0x1100, 0x1101}, // ㅇ + ㄱ = ㄲ
			{0x110b, 0x1102, 0x114c}, // ㅇ + ㄴ = ᅌ
			{0x110b, 0x1103, 0x1104}, // ㅇ + ㄷ = ㄸ
			{0x110b, 0x1105, 0xa976}, // ㅇ + ㄹ = ꥶ
			{0x110b, 0x1107, 0x1108}, // ㅇ + ㅂ = ㅃ
			{0x110b, 0x1109, 0x110a}, // ㅇ + ㅅ = ㅆ
			{0x110b, 0x110c, 0x110d}, // ㅇ + ㅈ = ㅉ
			{0x110b, 0x1112, 0x114a}, // ㅇ + ㅎ = ᅊ
			{0x110b, 0x112b, 0x112c}, // ㅇ + ᄫ = ᄬ
			{0x110c, 0x1100, 0x1152}, // ㅈ + ㄱ = ᅒ
			{0x110c, 0x1102, 0x1157}, // ㅈ + ㄴ = ᅗ
			{0x110c, 0x1103, 0xa963}, // ㅈ + ㄷ = ꥣ
			{0x110c, 0x1105, 0xa96d}, // ㅈ + ㄹ = ꥭ
			{0x110c, 0x1106, 0xa971}, // ㅈ + ㅁ = ꥱ
			{0x110c, 0x1107, 0x1127}, // ㅈ + ㅂ = ᄧ
			{0x110c, 0x1109, 0x1136}, // ㅈ + ㅅ = ᄶ
			{0x110c, 0x110b, 0x110d}, // ㅈ + ㅇ = ㅉ
			{0x110c, 0x110c, 0x110d}, // ㅈ + ㅈ = ㅉ
			{0x110c, 0x1112, 0x110e}, // ㅈ + ㅎ = ㅊ
			{0x110c, 0x1121, 0x1150}, // ㅈ + ᄡ = ᅐ
			{0x110c, 0x1140, 0x114e}, // ㅈ + ᅀ = ᅎ
			{0x110c, 0x1147, 0x110d}, // ㅈ + ᅇ = ㅉ
			{0x110c, 0x1154, 0x114f}, // ㅈ + ᅔ = ᅏ
			{0x110c, 0x1155, 0x1151}, // ㅈ + ᅕ = ᅑ
			{0x110d, 0x110c, 0x110e}, // ㅉ + ㅈ = ㅊ
			{0x110d, 0x1121, 0x1151}, // ㅉ + ᄡ = ᅑ
			{0x110d, 0x1140, 0x114f}, // ㅉ + ᅀ = ᅏ
			{0x1112, 0x1100, 0x110f}, // ㅎ + ㄱ = ㅋ
			{0x1112, 0x1102, 0x1159}, // ㅎ + ㄴ = ᅙ
			{0x1112, 0x1103, 0x1110}, // ㅎ + ㄷ = ㅌ
			{0x1112, 0x1105, 0x111a}, // ㅎ + ㄹ = ᄚ
			{0x1112, 0x1106, 0x1143}, // ㅎ + ㅁ = ᅃ
			{0x1112, 0x1107, 0x1111}, // ㅎ + ㅂ = ㅍ
			{0x1112, 0x1109, 0x1132}, // ㅎ + ㅅ = ᄲ
			{0x1112, 0x110b, 0x114a}, // ㅎ + ㅇ = ᅊ
			{0x1112, 0x110c, 0x110e}, // ㅎ + ㅈ = ㅊ
			{0x1112, 0x1147, 0x1158}, // ㅎ + ᅇ = ᅘ
			{0x1121, 0x1100, 0x1122}, // ᄡ + ㄱ = ᄢ
			{0x1121, 0x1102, 0x114b}, // ᄡ + ㄴ = ᅋ
			{0x1121, 0x1103, 0x1123}, // ᄡ + ㄷ = ᄣ
			{0x1121, 0x1109, 0x113e}, // ᄡ + ㅅ = ᄾ
			{0x1121, 0x110c, 0x1150}, // ᄡ + ㅈ = ᅐ
			{0x1121, 0x110d, 0x1151}, // ᄡ + ㅉ = ᅑ
			{0x1121, 0x1145, 0x113f}, // ᄡ + ᅅ = ᄿ
			{0x1121, 0x1147, 0x1155}, // ᄡ + ᅇ = ᅕ
			{0x112b, 0x110b, 0x112c}, // ᄫ + ㅇ = ᄬ
			{0x113c, 0x1147, 0x113d}, // ᄼ + ᅇ = ᄽ
			{0x113e, 0x1147, 0x113f}, // ᄾ + ᅇ = ᄿ
			{0x1140, 0x1102, 0xa976}, // ᅀ + ㄴ = ꥶ
			{0x1140, 0x1109, 0x113c}, // ᅀ + ㅅ = ᄼ
			{0x1140, 0x110c, 0x114e}, // ᅀ + ㅈ = ᅎ
			{0x1140, 0x110d, 0x114f}, // ᅀ + ㅉ = ᅏ
			{0x1140, 0x1145, 0x113d}, // ᅀ + ᅅ = ᄽ
			{0x1140, 0x1147, 0x1154}, // ᅀ + ᅇ = ᅔ
			{0x1145, 0x1121, 0x113f}, // ᅅ + ᄡ = ᄿ
			{0x1145, 0x1140, 0x113d}, // ᅅ + ᅀ = ᄽ
			{0x1147, 0x1100, 0x1141}, // ᅇ + ㄱ = ᅁ
			{0x1147, 0x1102, 0x1114}, // ᅇ + ㄴ = ᄔ
			{0x1147, 0x1103, 0x1142}, // ᅇ + ㄷ = ᅂ
			{0x1147, 0x1105, 0x1119}, // ᅇ + ㄹ = ᄙ
			{0x1147, 0x1107, 0x1144}, // ᅇ + ㅂ = ᅄ
			{0x1147, 0x1109, 0x1145}, // ᅇ + ㅅ = ᅅ
			{0x1147, 0x110c, 0x110d}, // ᅇ + ㅈ = ㅉ
			{0x1147, 0x1112, 0x1158}, // ᅇ + ㅎ = ᅘ
			{0x1147, 0x1121, 0x1155}, // ᅇ + ᄡ = ᅕ
			{0x1147, 0x113c, 0x113d}, // ᅇ + ᄼ = ᄽ
			{0x1147, 0x113e, 0x113f}, // ᅇ + ᄾ = ᄿ
			{0x1147, 0x1140, 0x1154}, // ᅇ + ᅀ = ᅔ
			{0x1147, 0x114e, 0x114f}, // ᅇ + ᅎ = ᅏ
			{0x1147, 0x1150, 0x1151}, // ᅇ + ᅐ = ᅑ
			{0x114c, 0x1107, 0x112c}, // ᅌ + ㅂ = ᄬ
			{0x114e, 0x1147, 0x114f}, // ᅎ + ᅇ = ᅏ
			{0x1150, 0x1147, 0x1151}, // ᅐ + ᅇ = ᅑ
			{0x1154, 0x1109, 0x113d}, // ᅔ + ㅅ = ᄽ
			{0x1154, 0x110c, 0x114f}, // ᅔ + ㅈ = ᅏ
			{0x1155, 0x1109, 0x113f}, // ᅕ + ㅅ = ᄿ
			{0x1155, 0x110c, 0x1151}, // ᅕ + ㅈ = ᅑ

			{0x1161, 0x1161, 0x1163}, // ㅏ + ㅏ = ㅑ
			{0x1161, 0x1165, 0x116d}, // ㅏ + ㅓ = ㅛ
			{0x1161, 0x1166, 0x1198}, // ㅏ + ㅔ = ᆘ
			{0x1161, 0x1169, 0x116a}, // ㅏ + ㅗ = ㅘ
			{0x1161, 0x116c, 0x116b}, // ㅏ + ㅚ = ㅙ
			{0x1161, 0x116e, 0x1189}, // ㅏ + ㅜ = ᆉ
			{0x1161, 0x1173, 0x119e}, // ㅏ + ㅡ = ㆍ
			{0x1161, 0x1174, 0x11a1}, // ㅏ + ㅢ = ㆎ
			{0x1161, 0x1175, 0x1162}, // ㅏ + ㅣ = ㅐ
			{0x1161, 0x1188, 0x1176}, // ㅏ + ᆈ = ᅶ
			{0x1161, 0x1194, 0x1177}, // ㅏ + ᆔ = ᅷ
			{0x1161, 0x011169, 0x116a}, // ㅏ + ㅗ = ㅘ
			{0x1162, 0x1169, 0x116b}, // ㅐ + ㅗ = ㅙ
			{0x1162, 0x1173, 0x11a1}, // ㅐ + ㅡ = ㆎ
			{0x1162, 0x011169, 0x116b}, // ㅐ + ㅗ = ㅙ
			{0x1163, 0x1173, 0x11a2}, // ㅑ + ㅡ = ᆢ
			{0x1165, 0x1161, 0x116d}, // ㅓ + ㅏ = ㅛ
			{0x1165, 0x1165, 0x116f}, // ㅓ + ㅓ = ㅝ
			{0x1165, 0x1166, 0xd7bf}, // ㅓ + ㅔ = ힿ
			{0x1165, 0x1167, 0xd7c4}, // ㅓ + ㅕ = ퟄ
			{0x1165, 0x1169, 0x116f}, // ㅓ + ㅗ = ㅝ
			{0x1165, 0x116e, 0x116f}, // ㅓ + ㅜ = ㅝ
			{0x1165, 0x1171, 0x1170}, // ㅓ + ㅟ = ㅞ
			{0x1165, 0x1173, 0x11a2}, // ㅓ + ㅡ = ᆢ
			{0x1165, 0x1188, 0x1184}, // ㅓ + ᆈ = ᆄ
			{0x1165, 0x11a1, 0x117a}, // ㅓ + ㆎ = ᅺ
			{0x1165, 0x011169, 0x116f}, // ㅓ + ㅗ = ㅝ
			{0x1166, 0x1161, 0x1198}, // ㅔ + ㅏ = ᆘ
			{0x1166, 0x1165, 0xd7bf}, // ㅔ + ㅓ = ힿ
			{0x1166, 0x1166, 0x1168}, // ㅔ + ㅔ = ㅖ
			{0x1166, 0x1167, 0xd7be}, // ㅔ + ㅕ = ힾ
			{0x1166, 0x1169, 0x1168}, // ㅔ + ㅗ = ㅖ
			{0x1166, 0x116e, 0x1170}, // ㅔ + ㅜ = ㅞ
			{0x1166, 0x1173, 0xd7bb}, // ㅔ + ㅡ = ힻ
			{0x1166, 0x1175, 0x1171}, // ㅔ + ㅣ = ㅟ
			{0x1166, 0x1188, 0x1180}, // ㅔ + ᆈ = ᆀ
			{0x1166, 0x1194, 0x1192}, // ㅔ + ᆔ = ᆒ
			{0x1166, 0x011169, 0x1168}, // ㅔ + ㅗ = ㅖ
			{0x1167, 0x1165, 0xd7c4}, // ㅕ + ㅓ = ퟄ
			{0x1167, 0x1166, 0xd7be}, // ㅕ + ㅔ = ힾ
			{0x1167, 0x1167, 0x1164}, // ㅕ + ㅕ = ㅒ
			{0x1167, 0x1169, 0x1164}, // ㅕ + ㅗ = ㅒ
			{0x1167, 0x116e, 0x117e}, // ㅕ + ㅜ = ᅾ
			{0x1167, 0x1173, 0xd7b0}, // ㅕ + ㅡ = ힰ
			{0x1167, 0x1188, 0x1185}, // ㅕ + ᆈ = ᆅ
			{0x1167, 0x1194, 0x1191}, // ㅕ + ᆔ = ᆑ
			{0x1167, 0x11a1, 0x117d}, // ㅕ + ㆎ = ᅽ
			{0x1167, 0x011169, 0x1164}, // ㅕ + ㅗ = ㅒ
			{0x1168, 0x1175, 0xd7c0}, // ㅖ + ㅣ = ퟀ
			{0x1169, 0x1161, 0x116a}, // ㅗ + ㅏ = ㅘ
			{0x1169, 0x1162, 0x116b}, // ㅗ + ㅐ = ㅙ
			{0x1169, 0x1165, 0x116f}, // ㅗ + ㅓ = ㅝ
			{0x1169, 0x1166, 0x1168}, // ㅗ + ㅔ = ㅖ
			{0x1169, 0x1167, 0x1164}, // ㅗ + ㅕ = ㅒ
			{0x1169, 0x1169, 0x116d}, // ㅗ + ㅗ = ㅛ
			{0x1169, 0x116e, 0x1172}, // ㅗ + ㅜ = ㅠ
			{0x1169, 0x1173, 0xd7bc}, // ㅗ + ㅡ = ힼ
			{0x1169, 0x1175, 0x116c}, // ㅗ + ㅣ = ㅚ
			{0x1169, 0x11a1, 0x1178}, // ㅗ + ㆎ = ᅸ
			{0x1169, 0x011169, 0x116d}, // ㅗ + ㅗ = ㅛ
			{0x116a, 0x1175, 0x116b}, // ㅘ + ㅣ = ㅙ
			{0x116c, 0x1161, 0x116b}, // ㅚ + ㅏ = ㅙ
			{0x116c, 0x1166, 0xd7c0}, // ㅚ + ㅔ = ퟀ
			{0x116c, 0x116e, 0xd7c0}, // ㅚ + ㅜ = ퟀ
			{0x116e, 0x1161, 0x1189}, // ㅜ + ㅏ = ᆉ
			{0x116e, 0x1165, 0x116f}, // ㅜ + ㅓ = ㅝ
			{0x116e, 0x1166, 0x1170}, // ㅜ + ㅔ = ㅞ
			{0x116e, 0x1167, 0x117e}, // ㅜ + ㅕ = ᅾ
			{0x116e, 0x1169, 0x1172}, // ㅜ + ㅗ = ㅠ
			{0x116e, 0x116e, 0x1172}, // ㅜ + ㅜ = ㅠ
			{0x116e, 0x1173, 0x1195}, // ㅜ + ㅡ = ᆕ
			{0x116e, 0x1175, 0x1171}, // ㅜ + ㅣ = ㅟ
			{0x116e, 0x011169, 0x1172}, // ㅜ + ㅗ = ㅠ
			{0x116f, 0x1175, 0x1170}, // ㅝ + ㅣ = ㅞ
			{0x1171, 0x1165, 0x1170}, // ㅟ + ㅓ = ㅞ
			{0x1171, 0x1166, 0xd7c0}, // ㅟ + ㅔ = ퟀ
			{0x1171, 0x1169, 0xd7c0}, // ㅟ + ㅗ = ퟀ
			{0x1171, 0x011169, 0xd7c0}, // ㅟ + ㅗ = ퟀ
			{0x1171, 0x116e, 0xd7c0}, // ㅟ + ㅜ = ퟀ
			{0x1172, 0x1175, 0xd7c0}, // ㅠ + ㅣ = ퟀ
			{0x1173, 0x1161, 0x119e}, // ㅡ + ㅏ = ㆍ
			{0x1173, 0x1162, 0x11a1}, // ㅡ + ㅐ = ㆎ
			{0x1173, 0x1163, 0x11a2}, // ㅡ + ㅑ = ᆢ
			{0x1173, 0x1165, 0x11a2}, // ㅡ + ㅓ = ᆢ
			{0x1173, 0x1166, 0xd7bb}, // ㅡ + ㅔ = ힻ
			{0x1173, 0x1167, 0xd7b0}, // ㅡ + ㅕ = ힰ
			{0x1173, 0x1169, 0xd7bc}, // ㅡ + ㅗ = ힼ
			{0x1173, 0x116e, 0x1195}, // ㅡ + ㅜ = ᆕ
			{0x1173, 0x1173, 0x1163}, // ㅡ + ㅡ = ㅑ
			{0x1173, 0x1175, 0x1174}, // ㅡ + ㅣ = ㅢ
			{0x1173, 0x11a1, 0xd7ba}, // ㅡ + ㆎ = ힺ
			{0x1173, 0x011169, 0x1163}, // ㅡ + ㅗ = ㅑ
			{0x1174, 0x1161, 0x11a1}, // ㅢ + ㅏ = ㆎ
			{0x1175, 0x1161, 0x1162}, // ㅣ + ㅏ = ㅐ
			{0x1175, 0x1166, 0x1171}, // ㅣ + ㅔ = ㅟ
			{0x1175, 0x1168, 0xd7c0}, // ㅣ + ㅖ = ퟀ
			{0x1175, 0x1169, 0x116c}, // ㅣ + ㅗ = ㅚ
			{0x1175, 0x116a, 0x116b}, // ㅣ + ㅘ = ㅙ
			{0x1175, 0x116e, 0x1171}, // ㅣ + ㅜ = ㅟ
			{0x1175, 0x116f, 0x1170}, // ㅣ + ㅝ = ㅞ
			{0x1175, 0x1172, 0xd7c0}, // ㅣ + ㅠ = ퟀ
			{0x1175, 0x1173, 0x1174}, // ㅣ + ㅡ = ㅢ
			{0x1175, 0x119e, 0x11a1}, // ㅣ + ㆍ = ㆎ
			{0x1175, 0x11a1, 0x119c}, // ㅣ + ㆎ = ᆜ
			{0x1175, 0x011169, 0x116c}, // ㅣ + ㅗ = ㅚ
			{0x1188, 0x1161, 0x1176}, // ᆈ + ㅏ = ᅶ
			{0x1188, 0x1165, 0x1184}, // ᆈ + ㅓ = ᆄ
			{0x1188, 0x1166, 0x1180}, // ᆈ + ㅔ = ᆀ
			{0x1188, 0x1167, 0x1185}, // ᆈ + ㅕ = ᆅ
			{0x1194, 0x1161, 0x1177}, // ᆔ + ㅏ = ᅷ
			{0x1194, 0x1166, 0x1192}, // ᆔ + ㅔ = ᆒ
			{0x1194, 0x1167, 0x1191}, // ᆔ + ㅕ = ᆑ
			{0x119e, 0x1175, 0x11a1}, // ㆍ + ㅣ = ㆎ
			{0x119e, 0x119e, 0x11a2}, // ㆍ + ㆍ = ᆢ
			{0x11a1, 0x1165, 0x117a}, // ㆎ + ㅓ = ᅺ
			{0x11a1, 0x1167, 0x117d}, // ㆎ + ㅕ = ᅽ
			{0x11a1, 0x1169, 0x1178}, // ㆎ + ㅗ = ᅸ
			{0x11a1, 0x1173, 0xd7ba}, // ㆎ + ㅡ = ힺ
			{0x11a1, 0x1175, 0x119c}, // ㆎ + ㅣ = ᆜ
			{0x11a1, 0x011169, 0x1178}, // ㆎ + ㅗ = ᅸ
			{0x011169, 0x1161, 0x116a}, // ㅗ + ㅏ = ㅘ
			{0x011169, 0x1162, 0x116b}, // ㅗ + ㅐ = ㅙ
			{0x011169, 0x1165, 0x116f}, // ㅗ + ㅓ = ㅝ
			{0x011169, 0x1166, 0x1168}, // ㅗ + ㅔ = ㅖ
			{0x011169, 0x1167, 0x1164}, // ㅗ + ㅕ = ㅒ
			{0x011169, 0x1169, 0x116d}, // ㅗ + ㅗ = ㅛ
			{0x011169, 0x116e, 0x1172}, // ㅗ + ㅜ = ㅠ
			{0x011169, 0x1173, 0x1163}, // ㅗ + ㅡ = ㅑ
			{0x011169, 0x1175, 0x116c}, // ㅗ + ㅣ = ㅚ
			{0x011169, 0x11a1, 0x1178}, // ㅗ + ㆎ = ᅸ
			{0x011169, 0x011169, 0x116d}, // ㅗ + ㅗ = ㅛ

			{0x11a8, 0x11a8, 0x11a9}, // ㄱ + ㄱ = ㄲ
			{0x11a8, 0x11ab, 0x11fa}, // ㄱ + ㄴ = ᇺ
			{0x11a8, 0x11ae, 0x11aa}, // ㄱ + ㄷ = ㄳ
			{0x11a8, 0x11af, 0x11b0}, // ㄱ + ㄹ = ㄺ
			{0x11a8, 0x11b3, 0x11cc}, // ㄱ + ㄽ = ᇌ
			{0x11a8, 0x11b7, 0x11b0}, // ㄱ + ㅁ = ㄺ
			{0x11a8, 0x11b8, 0x11fb}, // ㄱ + ㅂ = ᇻ
			{0x11a8, 0x11ba, 0x11aa}, // ㄱ + ㅅ = ㄳ
			{0x11a8, 0x11bb, 0x11bf}, // ㄱ + ㅆ = ㅋ
			{0x11a8, 0x11bc, 0x11a9}, // ㄱ + ㅇ = ㄲ
			{0x11a8, 0x11c2, 0x11bf}, // ㄱ + ㅎ = ㅋ
			{0x11a8, 0x11d9, 0x11e7}, // ㄱ + ᇙ = ᇧ
			{0x11a9, 0x11a8, 0x11bf}, // ㄲ + ㄱ = ㅋ
			{0x11ab, 0x11a8, 0x11fa}, // ㄴ + ㄱ = ᇺ
			{0x11ab, 0x11ab, 0x11c2}, // ㄴ + ㄴ = ㅎ
			{0x11ab, 0x11af, 0x11ac}, // ㄴ + ㄹ = ㄵ
			{0x11ab, 0x11b7, 0x11c0}, // ㄴ + ㅁ = ㅌ
			{0x11ab, 0x11b8, 0x11c1}, // ㄴ + ㅂ = ㅍ
			{0x11ab, 0x11ba, 0x11c7}, // ㄴ + ㅅ = ᇇ
			{0x11ab, 0x11bb, 0x11c2}, // ㄴ + ㅆ = ㅎ
			{0x11ab, 0x11bc, 0x11ad}, // ㄴ + ㅇ = ㄶ
			{0x11ab, 0x11bd, 0x11ac}, // ㄴ + ㅈ = ㄵ
			{0x11ab, 0x11c2, 0x11ad}, // ㄴ + ㅎ = ㄶ
			{0x11ab, 0x11d9, 0x11c6}, // ㄴ + ᇙ = ᇆ
			{0x11ab, 0x11eb, 0x11c8}, // ㄴ + ᇫ = ᇈ
			{0x11ac, 0x11ab, 0x11b6}, // ㄵ + ㄴ = ㅀ
			{0x11ae, 0x11a8, 0x11aa}, // ㄷ + ㄱ = ㄳ
			{0x11ae, 0x11b6, 0x11b4}, // ㄷ + ㅀ = ㄾ
			{0x11ae, 0x11b7, 0x11c0}, // ㄷ + ㅁ = ㅌ
			{0x11ae, 0x11bc, 0x11b4}, // ㄷ + ㅇ = ㄾ
			{0x11ae, 0x11c2, 0x11c0}, // ㄷ + ㅎ = ㅌ
			{0x11af, 0x11a8, 0x11b0}, // ㄹ + ㄱ = ㄺ
			{0x11af, 0x11ab, 0x11ac}, // ㄹ + ㄴ = ㄵ
			{0x11af, 0x11af, 0x11bd}, // ㄹ + ㄹ = ㅈ
			{0x11af, 0x11b7, 0x11b1}, // ㄹ + ㅁ = ㄻ
			{0x11af, 0x11b8, 0x11b2}, // ㄹ + ㅂ = ㄼ
			{0x11af, 0x11ba, 0x11b3}, // ㄹ + ㅅ = ㄽ
			{0x11af, 0x11bb, 0x11bd}, // ㄹ + ㅆ = ㅈ
			{0x11af, 0x11bc, 0x11a8}, // ㄹ + ㅇ = ㄱ
			{0x11af, 0x11be, 0x11a9}, // ㄹ + ㅊ = ㄲ
			{0x11af, 0x11c0, 0x11b4}, // ㄹ + ㅌ = ㄾ
			{0x11af, 0x11c1, 0x11b5}, // ㄹ + ㅍ = ㄿ
			{0x11af, 0x11c2, 0x11b6}, // ㄹ + ㅎ = ㅀ
			{0x11af, 0x11d9, 0x11ce}, // ㄹ + ᇙ = ᇎ
			{0x11af, 0x11eb, 0x11d7}, // ㄹ + ᇫ = ᇗ
			{0x11b0, 0x11bb, 0x11aa}, // ㄺ + ㅆ = ㄳ
			{0x11b1, 0x11b7, 0x11b4}, // ㄻ + ㅁ = ㄾ
			{0x11b2, 0x11b8, 0x11b5}, // ㄼ + ㅂ = ㄿ
			{0x11b2, 0x11bb, 0x11c0}, // ㄼ + ㅆ = ㅌ
			{0x11b3, 0x11a8, 0x11cc}, // ㄽ + ㄱ = ᇌ
			{0x11b3, 0x11b8, 0x11d3}, // ㄽ + ㅂ = ᇓ
			{0x11b3, 0x11ba, 0x11bd}, // ㄽ + ㅅ = ㅈ
			{0x11b3, 0x11bb, 0x11a9}, // ㄽ + ㅆ = ㄲ
			{0x11b3, 0x11d9, 0x11ea}, // ㄽ + ᇙ = ᇪ
			{0x11b6, 0x11ae, 0x11b4}, // ㅀ + ㄷ = ㄾ
			{0x11b6, 0x11af, 0x11bf}, // ㅀ + ㄹ = ㅋ
			{0x11b6, 0x11b8, 0x11b5}, // ㅀ + ㅂ = ㄿ
			{0x11b7, 0x11a8, 0x11b0}, // ㅁ + ㄱ = ㄺ
			{0x11b7, 0x11ab, 0x11c0}, // ㅁ + ㄴ = ㅌ
			{0x11b7, 0x11af, 0x11b1}, // ㅁ + ㄹ = ㄻ
			{0x11b7, 0x11b7, 0x11ae}, // ㅁ + ㅁ = ㄷ
			{0x11b7, 0x11b8, 0x11dc}, // ㅁ + ㅂ = ᇜ
			{0x11b7, 0x11ba, 0x11dd}, // ㅁ + ㅅ = ᇝ
			{0x11b7, 0x11bb, 0x11ae}, // ㅁ + ㅆ = ㄷ
			{0x11b7, 0x11bc, 0x11b4}, // ㅁ + ㅇ = ㄾ
			{0x11b7, 0x11bf, 0x11aa}, // ㅁ + ㅋ = ㄳ
			{0x11b7, 0x11d9, 0x11e2}, // ㅁ + ᇙ = ᇢ
			{0x11b7, 0x11eb, 0x11df}, // ㅁ + ᇫ = ᇟ
			{0x11b8, 0x11a8, 0x11fb}, // ㅂ + ㄱ = ᇻ
			{0x11b8, 0x11ab, 0x11c1}, // ㅂ + ㄴ = ㅍ
			{0x11b8, 0x11af, 0x11b2}, // ㅂ + ㄹ = ㄼ
			{0x11b8, 0x11b3, 0x11d3}, // ㅂ + ㄽ = ᇓ
			{0x11b8, 0x11b6, 0x11b5}, // ㅂ + ㅀ = ㄿ
			{0x11b8, 0x11b7, 0x11dc}, // ㅂ + ㅁ = ᇜ
			{0x11b8, 0x11b8, 0x11c1}, // ㅂ + ㅂ = ㅍ
			{0x11b8, 0x11ba, 0x11b9}, // ㅂ + ㅅ = ㅄ
			{0x11b8, 0x11bb, 0x11c1}, // ㅂ + ㅆ = ㅍ
			{0x11b8, 0x11bc, 0x11b5}, // ㅂ + ㅇ = ㄿ
			{0x11b8, 0x11bd, 0x11c0}, // ㅂ + ㅈ = ㅌ
			{0x11b8, 0x11be, 0x11b1}, // ㅂ + ㅊ = ㄻ
			{0x11b8, 0x11c2, 0x11c1}, // ㅂ + ㅎ = ㅍ
			{0x11b8, 0x11d9, 0x11e6}, // ㅂ + ᇙ = ᇦ
			{0x11b9, 0x11bb, 0x11b1}, // ㅄ + ㅆ = ㄻ
			{0x11ba, 0x11a8, 0x11aa}, // ㅅ + ㄱ = ㄳ
			{0x11ba, 0x11ab, 0x11c7}, // ㅅ + ㄴ = ᇇ
			{0x11ba, 0x11af, 0x11b3}, // ㅅ + ㄹ = ㄽ
			{0x11ba, 0x11b7, 0x11dd}, // ㅅ + ㅁ = ᇝ
			{0x11ba, 0x11b8, 0x11b9}, // ㅅ + ㅂ = ㅄ
			{0x11ba, 0x11ba, 0x11bb}, // ㅅ + ㅅ = ㅆ
			{0x11ba, 0x11bb, 0x11be}, // ㅅ + ㅆ = ㅊ
			{0x11ba, 0x11bc, 0x11bb}, // ㅅ + ㅇ = ㅆ
			{0x11ba, 0x11bd, 0x11a9}, // ㅅ + ㅈ = ㄲ
			{0x11ba, 0x11c1, 0x11b1}, // ㅅ + ㅍ = ㄻ
			{0x11ba, 0x11d9, 0x11e8}, // ㅅ + ᇙ = ᇨ
			{0x11ba, 0x11f0, 0x11f1}, // ㅅ + ᇰ = ᇱ
			{0x11bb, 0x11a8, 0x11bf}, // ㅆ + ㄱ = ㅋ
			{0x11bb, 0x11ab, 0x11c2}, // ㅆ + ㄴ = ㅎ
			{0x11bb, 0x11ae, 0x11c0}, // ㅆ + ㄷ = ㅌ
			{0x11bb, 0x11af, 0x11bd}, // ㅆ + ㄹ = ㅈ
			{0x11bb, 0x11b0, 0x11aa}, // ㅆ + ㄺ = ㄳ
			{0x11bb, 0x11b2, 0x11c0}, // ㅆ + ㄼ = ㅌ
			{0x11bb, 0x11b3, 0x11a9}, // ㅆ + ㄽ = ㄲ
			{0x11bb, 0x11b7, 0x11ae}, // ㅆ + ㅁ = ㄷ
			{0x11bb, 0x11b8, 0x11c1}, // ㅆ + ㅂ = ㅍ
			{0x11bb, 0x11b9, 0x11b1}, // ㅆ + ㅄ = ㄻ
			{0x11bb, 0x11ba, 0x11be}, // ㅆ + ㅅ = ㅊ
			{0x11bb, 0x11bc, 0x11b6}, // ㅆ + ㅇ = ㅀ
			{0x11bc, 0x11a8, 0x11a9}, // ㅇ + ㄱ = ㄲ
			{0x11bc, 0x11ab, 0x11ad}, // ㅇ + ㄴ = ㄶ
			{0x11bc, 0x11af, 0x11a8}, // ㅇ + ㄹ = ㄱ
			{0x11bc, 0x11b7, 0x11b4}, // ㅇ + ㅁ = ㄾ
			{0x11bc, 0x11b8, 0x11b5}, // ㅇ + ㅂ = ㄿ
			{0x11bc, 0x11ba, 0x11bb}, // ㅇ + ㅅ = ㅆ
			{0x11bc, 0x11bb, 0x11b6}, // ㅇ + ㅆ = ㅀ
			{0x11bc, 0x11bc, 0x11c0}, // ㅇ + ㅇ = ㅌ
			{0x11bc, 0x11d9, 0x11f4}, // ㅇ + ᇙ = ᇴ
			{0x11bd, 0x11b8, 0x11c0}, // ㅈ + ㅂ = ㅌ
			{0x11bd, 0x11ba, 0x11a9}, // ㅈ + ㅅ = ㄲ
			{0x11bd, 0x11bc, 0x11bf}, // ㅈ + ㅇ = ㅋ
			{0x11be, 0x11af, 0x11a9}, // ㅊ + ㄹ = ㄲ
			{0x11be, 0x11b8, 0x11b1}, // ㅊ + ㅂ = ㄻ
			{0x11bf, 0x11b7, 0x11aa}, // ㅋ + ㅁ = ㄳ
			{0x11c0, 0x11bc, 0x11b6}, // ㅌ + ㅇ = ㅀ
			{0x11c1, 0x11af, 0x11c0}, // ㅍ + ㄹ = ㅌ
			{0x11c1, 0x11ba, 0x11b1}, // ㅍ + ㅅ = ㄻ
			{0x11c2, 0x11a8, 0x11bf}, // ㅎ + ㄱ = ㅋ
			{0x11c2, 0x11ab, 0x11ad}, // ㅎ + ㄴ = ㄶ
			{0x11c2, 0x11ae, 0x11c0}, // ㅎ + ㄷ = ㅌ
			{0x11c2, 0x11b8, 0x11c1}, // ㅎ + ㅂ = ㅍ
			{0x11d9, 0x11a8, 0x11e7}, // ᇙ + ㄱ = ᇧ
			{0x11d9, 0x11ab, 0x11c6}, // ᇙ + ㄴ = ᇆ
			{0x11d9, 0x11af, 0x11ce}, // ᇙ + ㄹ = ᇎ
			{0x11d9, 0x11b3, 0x11ea}, // ᇙ + ㄽ = ᇪ
			{0x11d9, 0x11b7, 0x11e2}, // ᇙ + ㅁ = ᇢ
			{0x11d9, 0x11b8, 0x11e6}, // ᇙ + ㅂ = ᇦ
			{0x11d9, 0x11ba, 0x11e8}, // ᇙ + ㅅ = ᇨ
			{0x11d9, 0x11bc, 0x11f4}, // ᇙ + ㅇ = ᇴ
			{0x11d9, 0x11eb, 0xd7d7}, // ᇙ + ᇫ = ퟗ
			{0x11d9, 0x11f0, 0x11f9}, // ᇙ + ᇰ = ᇹ
			{0x11eb, 0x11ab, 0x11c8}, // ᇫ + ㄴ = ᇈ
			{0x11eb, 0x11af, 0x11d7}, // ᇫ + ㄹ = ᇗ
			{0x11eb, 0x11b7, 0x11df}, // ᇫ + ㅁ = ᇟ
			{0x11eb, 0x11d9, 0xd7d7}, // ᇫ + ᇙ = ퟗ
			{0x11eb, 0x11f0, 0x11f2}, // ᇫ + ᇰ = ᇲ
			{0x11f0, 0x11ba, 0x11f1}, // ᇰ + ㅅ = ᇱ
			{0x11f0, 0x11d9, 0x11f9}, // ᇰ + ᇙ = ᇹ
			{0x11f0, 0x11eb, 0x11f2}, // ᇰ + ᇫ = ᇲ
	};

	public static final SparseArray<String> SEMOE_ABBREVIATIONS = createSemoeAbbreviations();

	private static SparseArray<String> createSemoeAbbreviations() {
		SparseArray<String> abbreviations = new SparseArray<String>(1363); // 세모이 자판의 총 일반 약어 1363개
		// 약어에 쓰이는 옛한글 글자를 유니코드 순으로 정렬
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11a8, "국가"); // ᄀᅠᆨ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11a9, "공격"); // ᄀᅠᆩ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11ab, "기는 "); // ᄀᅠᆫ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11ac, "건물"); // ᄀᅠᆬ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11ad, "공간"); // ᄀᅠᆭ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11af, "결과"); // ᄀᅠᆯ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11b0, "결국"); // ᄀᅠᆰ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11b5, "공급"); // ᄀᅠᆵ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11b7, "개념"); // ᄀᅠᆷ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11b8, "고급"); // ᄀᅠᆸ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11ba, "그것"); // ᄀᅠᆺ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11bc, "광고"); // ᄀᅠᆼ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11c0, "관념"); // ᄀᅠᇀ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11c1, "깊은 "); // ᄀᅠᇁ
		appendAbbreviation(abbreviations, 0x1100, 0x1160, 0x11fa, "국민"); // ᄀᅠᇺ
		appendAbbreviation(abbreviations, 0x1100, 0x1165, 0x11fa, "객관적"); // 거ᇺ
		appendAbbreviation(abbreviations, 0x1100, 0x116a, 0x11fa, "관객"); // 과ᇺ
		appendAbbreviation(abbreviations, 0x1100, 0x117e, 0, "겨우 "); // ᄀᅾ
		appendAbbreviation(abbreviations, 0x1100, 0x117e, 0x11bc, "구경"); // ᄀᅾᆼ
		appendAbbreviation(abbreviations, 0x1100, 0x1189, 0, "구나"); // ᄀᆉ
		appendAbbreviation(abbreviations, 0x1100, 0x1189, 0x11a8, "국내"); // ᄀᆉᆨ
		appendAbbreviation(abbreviations, 0x1100, 0x1195, 0, "궁금"); // ᄀᆕ
		appendAbbreviation(abbreviations, 0x1100, 0x1195, 0x11ab, "근무"); // ᄀᆕᆫ
		appendAbbreviation(abbreviations, 0x1100, 0x1198, 0, "가게"); // ᄀᆘ
		appendAbbreviation(abbreviations, 0x1100, 0x1198, 0x11ab, "관계"); // ᄀᆘᆫ
		appendAbbreviation(abbreviations, 0x1100, 0x119e, 0, "가는 "); // ᄀᆞ
		appendAbbreviation(abbreviations, 0x1100, 0x119e, 0x11ab, "그만"); // ᄀᆞᆫ
		appendAbbreviation(abbreviations, 0x1100, 0x119e, 0x11b7, "같은 "); // ᄀᆞᆷ
		appendAbbreviation(abbreviations, 0x1100, 0x11a2, 0, "것은 "); // ᄀᆢ
		appendAbbreviation(abbreviations, 0x1100, 0x11a2, 0x11ab, "근거"); // ᄀᆢᆫ
		appendAbbreviation(abbreviations, 0x1100, 0x11a2, 0x11af, "것을 "); // ᄀᆢᆯ
		appendAbbreviation(abbreviations, 0x1100, 0x11a2, 0x11b7, "검은 "); // ᄀᆢᆷ
		appendAbbreviation(abbreviations, 0x1100, 0xd7b0, 0, "그녀"); // ᄀힰ
		appendAbbreviation(abbreviations, 0x1100, 0xd7bb, 0, "계급"); // ᄀힻ
		appendAbbreviation(abbreviations, 0x1100, 0xd7bc, 0, "그곳"); // ᄀힼ
		appendAbbreviation(abbreviations, 0x1100, 0xd7be, 0, "경계"); // ᄀힾ
		appendAbbreviation(abbreviations, 0x1100, 0xd7bf, 0, "계약"); // ᄀힿ
		appendAbbreviation(abbreviations, 0x1100, 0xd7c0, 0, "기계"); // ᄀퟀ
		appendAbbreviation(abbreviations, 0x1100, 0xd7c0, 0x11a8, "계획"); // ᄀퟀᆨ
		appendAbbreviation(abbreviations, 0x1100, 0xd7c4, 0x11bc, "경험"); // ᄀퟄᆼ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11a8, "교육"); // ᄁᅠᆨ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11ab, "인간"); // ᄁᅠᆫ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11ac, "연결"); // ᄁᅠᆬ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11ad, "공연"); // ᄁᅠᆭ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11af, "얼굴"); // ᄁᅠᆯ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11b0, "엄격"); // ᄁᅠᆰ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11b5, "공업"); // ᄁᅠᆵ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11b7, "금융"); // ᄁᅠᆷ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11b8, "기업"); // ᄁᅠᆸ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11b9, "끊임없"); // ᄁᅠᆹ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11ba, "이것"); // ᄁᅠᆺ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11bc, "경우"); // ᄁᅠᆼ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11bd, "온갖 "); // ᄁᅠᆽ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11c0, "같이 "); // ᄁᅠᇀ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11c1, "깊이"); // ᄁᅠᇁ
		appendAbbreviation(abbreviations, 0x1101, 0x1160, 0x11fa, "약간"); // ᄁᅠᇺ
		appendAbbreviation(abbreviations, 0x1101, 0x1161, 0x11fa, "관악"); // 까ᇺ
		appendAbbreviation(abbreviations, 0x1101, 0x1169, 0x11fa, "외국인"); // 꼬ᇺ
		appendAbbreviation(abbreviations, 0x1101, 0x117e, 0, "연구"); // ᄁᅾ
		appendAbbreviation(abbreviations, 0x1101, 0x117e, 0x11af, "겨울"); // ᄁᅾᆯ
		appendAbbreviation(abbreviations, 0x1101, 0x1189, 0, "요구"); // ᄁᆉ
		appendAbbreviation(abbreviations, 0x1101, 0x1189, 0x11af, "개월"); // ᄁᆉᆯ
		appendAbbreviation(abbreviations, 0x1101, 0x1195, 0, "꾸는 "); // ᄁᆕ
		appendAbbreviation(abbreviations, 0x1101, 0x119e, 0, "을까? "); // ᄁᆞ
		appendAbbreviation(abbreviations, 0x1101, 0x119e, 0x11af, "가을"); // ᄁᆞᆯ
		appendAbbreviation(abbreviations, 0x1101, 0x119e, 0x11b7, "가끔 "); // ᄁᆞᆷ
		appendAbbreviation(abbreviations, 0x1101, 0x119e, 0x11ba, "깨끗"); // ᄁᆞᆺ
		appendAbbreviation(abbreviations, 0x1101, 0x11a2, 0, "거의 "); // ᄁᆢ
		appendAbbreviation(abbreviations, 0x1101, 0x11a2, 0x11ab, "근원"); // ᄁᆢᆫ
		appendAbbreviation(abbreviations, 0x1101, 0x11a2, 0x11af, "걸음"); // ᄁᆢᆯ
		appendAbbreviation(abbreviations, 0x1101, 0xd7b0, 0, "연극"); // ᄁힰ
		appendAbbreviation(abbreviations, 0x1101, 0xd7b0, 0x11ab, "의견"); // ᄁힰᆫ
		appendAbbreviation(abbreviations, 0x1101, 0xd7bb, 0, "예금"); // ᄁힻ
		appendAbbreviation(abbreviations, 0x1101, 0xd7bc, 0, "요금"); // ᄁힼ
		appendAbbreviation(abbreviations, 0x1101, 0xd7bf, 0, "께서 "); // ᄁힿ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11a8, "녀석"); // ᄂᅠᆨ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11ab, "내년"); // ᄂᅠᆫ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11ac, "눈길"); // ᄂᅠᆬ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11af, "그날"); // ᄂᅠᆯ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11b2, "넓은 "); // ᄂᅠᆲ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11b7, "남성"); // ᄂᅠᆷ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11bc, "가능"); // ᄂᅠᆼ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11be, "눈빛"); // ᄂᅠᆾ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11c1, "높이"); // ᄂᅠᇁ
		appendAbbreviation(abbreviations, 0x1102, 0x1160, 0x11c2, "내놓"); // ᄂᅠᇂ
		appendAbbreviation(abbreviations, 0x1102, 0x1189, 0, "누나"); // ᄂᆉ
		appendAbbreviation(abbreviations, 0x1102, 0x1198, 0, "나게 "); // ᄂᆘ
		appendAbbreviation(abbreviations, 0x1102, 0x119e, 0, "나는 "); // ᄂᆞ
		appendAbbreviation(abbreviations, 0x1102, 0x119e, 0x11ab, "는가"); // ᄂᆞᆫ
		appendAbbreviation(abbreviations, 0x1102, 0x119e, 0x11af, "낮은 "); // ᄂᆞᆯ
		appendAbbreviation(abbreviations, 0x1102, 0x119e, 0x11bc, "않는 "); // ᄂᆞᆼ
		appendAbbreviation(abbreviations, 0x1102, 0x11a1, 0, "내는 "); // ᄂᆡ
		appendAbbreviation(abbreviations, 0x1102, 0xd7bc, 0, "느냐? "); // ᄂힼ
		appendAbbreviation(abbreviations, 0x1102, 0xd7bc, 0x11ab, "고는 "); // ᄂힼᆫ
		appendAbbreviation(abbreviations, 0x1102, 0xd7bc, 0x11bc, "그냥 "); // ᄂힼᆼ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11a8, "대학"); // ᄃᅠᆨ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11ab, "다른 "); // ᄃᅠᆫ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11ac, "들은 "); // ᄃᅠᆬ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11ad, "당연"); // ᄃᅠᆭ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11af, "들이 "); // ᄃᅠᆯ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11b4, "담당"); // ᄃᅠᆴ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11b7, "다음"); // ᄃᅠᆷ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11b8, "대답"); // ᄃᅠᆸ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11ba, "듯이 "); // ᄃᅠᆺ
		appendAbbreviation(abbreviations, 0x1103, 0x1160, 0x11bc, "다양"); // ᄃᅠᆼ
		appendAbbreviation(abbreviations, 0x1103, 0x1189, 0, "무대"); // ᄃᆉ
		appendAbbreviation(abbreviations, 0x1103, 0x1189, 0x11ab, "다운 "); // ᄃᆉᆫ
		appendAbbreviation(abbreviations, 0x1103, 0x1195, 0, "문득 "); // ᄃᆕ
		appendAbbreviation(abbreviations, 0x1103, 0x1198, 0x11ab, "단체"); // ᄃᆘᆫ
		appendAbbreviation(abbreviations, 0x1103, 0x119e, 0, "만들"); // ᄃᆞ
		appendAbbreviation(abbreviations, 0x1103, 0x11a2, 0x11af, "들어"); // ᄃᆢᆯ
		appendAbbreviation(abbreviations, 0x1103, 0xd7bc, 0, "독특"); // ᄃힼ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11a8, "더욱 "); // ᄄᅠᆨ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11ab, "때문"); // ᄄᅠᆫ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11ac, "일단 "); // ᄄᅠᆬ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11ad, "동안"); // ᄄᅠᆭ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11af, "들의 "); // ᄄᅠᆯ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11b0, "독일"); // ᄄᅠᆰ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11b7, "도움"); // ᄄᅠᆷ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11b8, "도입"); // ᄄᅠᆸ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11ba, "되었"); // ᄄᅠᆺ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11bc, "운동"); // ᄄᅠᆼ
		appendAbbreviation(abbreviations, 0x1104, 0x1160, 0x11c2, "어떻"); // ᄄᅠᇂ
		appendAbbreviation(abbreviations, 0x1104, 0x1198, 0, "에다 "); // ᄄᆘ
		appendAbbreviation(abbreviations, 0x1104, 0x119e, 0, "따뜻"); // ᄄᆞ
		appendAbbreviation(abbreviations, 0x1104, 0x11a2, 0, "드디어 "); // ᄄᆢ
		appendAbbreviation(abbreviations, 0x1104, 0x11a2, 0x11af, "떠들"); // ᄄᆢᆯ
		appendAbbreviation(abbreviations, 0x1104, 0xd7bb, 0, "은데"); // ᄄힻ
		appendAbbreviation(abbreviations, 0x1104, 0xd7bc, 0, "의도"); // ᄄힼ
		appendAbbreviation(abbreviations, 0x1105, 0x1160, 0x11ab, "이런 "); // ᄅᅠᆫ
		appendAbbreviation(abbreviations, 0x1105, 0x1160, 0x11af, "달러"); // ᄅᅠᆯ
		appendAbbreviation(abbreviations, 0x1105, 0x1160, 0x11b7, "여름"); // ᄅᅠᆷ
		appendAbbreviation(abbreviations, 0x1105, 0x117e, 0, "우려"); // ᄅᅾ
		appendAbbreviation(abbreviations, 0x1105, 0x1189, 0, "아무리 "); // ᄅᆉ
		appendAbbreviation(abbreviations, 0x1105, 0x1195, 0, "루는 "); // ᄅᆕ
		appendAbbreviation(abbreviations, 0x1105, 0x1198, 0, "라게 "); // ᄅᆘ
		appendAbbreviation(abbreviations, 0x1105, 0x119e, 0, "라는 "); // ᄅᆞ
		appendAbbreviation(abbreviations, 0x1105, 0x11a2, 0, "그러므로 "); // ᄅᆢ
		appendAbbreviation(abbreviations, 0x1105, 0xd7c4, 0, "여러 "); // ᄅퟄ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11a8, "미국"); // ᄆᅠᆨ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11ab, "문화"); // ᄆᅠᆫ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11ac, "물건"); // ᄆᅠᆬ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11ad, "많이 "); // ᄆᅠᆭ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11ae, "믿음"); // ᄆᅠᆮ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11af, "물질"); // ᄆᅠᆯ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11b0, "골목"); // ᄆᅠᆰ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11b7, "마음"); // ᄆᅠᆷ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11b8, "업무"); // ᄆᅠᆸ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11ba, "무엇"); // ᄆᅠᆺ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11bb, "맛있"); // ᄆᅠᆻ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11bc, "모양"); // ᄆᅠᆼ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11c1, "무릎"); // ᄆᅠᇁ
		appendAbbreviation(abbreviations, 0x1106, 0x1160, 0x11fa, "만약"); // ᄆᅠᇺ
		appendAbbreviation(abbreviations, 0x1106, 0x117e, 0, "무역"); // ᄆᅾ
		appendAbbreviation(abbreviations, 0x1106, 0x117e, 0x11ab, "운명"); // ᄆᅾᆫ
		appendAbbreviation(abbreviations, 0x1106, 0x117e, 0x11b8, "무렵"); // ᄆᅾᆸ
		appendAbbreviation(abbreviations, 0x1106, 0x117e, 0x11bc, "유명"); // ᄆᅾᆼ
		appendAbbreviation(abbreviations, 0x1106, 0x1189, 0, "매우 "); // ᄆᆉ
		appendAbbreviation(abbreviations, 0x1106, 0x1189, 0x11ab, "대문"); // ᄆᆉᆫ
		appendAbbreviation(abbreviations, 0x1106, 0x1189, 0x11af, "물가"); // ᄆᆉᆯ
		appendAbbreviation(abbreviations, 0x1106, 0x1195, 0, "의무"); // ᄆᆕ
		appendAbbreviation(abbreviations, 0x1106, 0x119e, 0, "마을"); // ᄆᆞ
		appendAbbreviation(abbreviations, 0x1106, 0x119e, 0x11ab, "많은 "); // ᄆᆞᆫ
		appendAbbreviation(abbreviations, 0x1106, 0x11a2, 0, "먹는 "); // ᄆᆢ
		appendAbbreviation(abbreviations, 0x1106, 0xd7bc, 0, "모든 "); // ᄆힼ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11a9, "밖에 "); // ᄇᅠᆩ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11ab, "부분"); // ᄇᅠᆫ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11ac, "불안"); // ᄇᅠᆬ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11ae, "받아"); // ᄇᅠᆮ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11af, "발표"); // ᄇᅠᆯ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11b2, "불법"); // ᄇᅠᆲ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11b7, "범위"); // ᄇᅠᆷ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11b8, "방법"); // ᄇᅠᆸ
		appendAbbreviation(abbreviations, 0x1107, 0x1160, 0x11bf, "부엌"); // ᄇᅠᆿ
		appendAbbreviation(abbreviations, 0x1107, 0x1189, 0, "바위"); // ᄇᆉ
		appendAbbreviation(abbreviations, 0x1107, 0x1189, 0x11ab, "후반"); // ᄇᆉᆫ
		appendAbbreviation(abbreviations, 0x1107, 0x119e, 0, "받은 "); // ᄇᆞ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11a8, "파악"); // ᄈᅠᆨ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11ab, "이번 "); // ᄈᅠᆫ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11ac, "일반"); // ᄈᅠᆬ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11ad, "방안"); // ᄈᅠᆭ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11af, "일본"); // ᄈᅠᆯ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11ba, "벗어"); // ᄈᅠᆺ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11bc, "병원"); // ᄈᅠᆼ
		appendAbbreviation(abbreviations, 0x1108, 0x1160, 0x11c1, "법원"); // ᄈᅠᇁ
		appendAbbreviation(abbreviations, 0x1108, 0x1189, 0, "배우"); // ᄈᆉ
		appendAbbreviation(abbreviations, 0x1108, 0x1189, 0x11ab, "위반"); // ᄈᆉᆫ
		appendAbbreviation(abbreviations, 0x1108, 0x1198, 0, "예방"); // ᄈᆘ
		appendAbbreviation(abbreviations, 0x1108, 0xd7bc, 0, "의복"); // ᄈힼ
		appendAbbreviation(abbreviations, 0x1108, 0xd7c4, 0, "여러분"); // ᄈퟄ
		appendAbbreviation(abbreviations, 0x1108, 0xd7c4, 0x11bc, "평양"); // ᄈퟄᆼ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11a8, "식사"); // ᄉᅠᆨ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11a9, "상식"); // ᄉᅠᆩ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11ab, "선생"); // ᄉᅠᆫ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11ad, "생산"); // ᄉᅠᆭ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11af, "사실"); // ᄉᅠᆯ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11b7, "사람"); // ᄉᅠᆷ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11b8, "습니다. "); // ᄉᅠᆸ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11b9, " 수 없"); // ᄉᅠᆹ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11ba, " 수 있"); // ᄉᅠᆺ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11bc, "세상"); // ᄉᅠᆼ
		appendAbbreviation(abbreviations, 0x1109, 0x1160, 0x11c1, "싶은 "); // ᄉᅠᇁ
		appendAbbreviation(abbreviations, 0x1109, 0x1189, 0, "수사"); // ᄉᆉ
		appendAbbreviation(abbreviations, 0x1109, 0x1189, 0x11bc, "수상"); // ᄉᆉᆼ
		appendAbbreviation(abbreviations, 0x1109, 0x1198, 0, "세요"); // ᄉᆘ
		appendAbbreviation(abbreviations, 0x1109, 0x119e, 0, "사는 "); // ᄉᆞ
		appendAbbreviation(abbreviations, 0x1109, 0x11a2, 0, "서는 "); // ᄉᆢ
		appendAbbreviation(abbreviations, 0x1109, 0xd7bc, 0, "소스"); // ᄉힼ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11a8, "역사"); // ᄊᅠᆨ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11a9, "양식"); // ᄊᅠᆩ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11ab, "우선 "); // ᄊᅠᆫ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11ad, "인생"); // ᄊᅠᆭ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11af, "서울"); // ᄊᅠᆯ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11b1, "열심"); // ᄊᅠᆱ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11b2, "입술"); // ᄊᅠᆲ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11b7, "예수님"); // ᄊᅠᆷ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11b8, "사업"); // ᄊᅠᆸ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11bc, "이상"); // ᄊᅠᆼ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11c0, "임신"); // ᄊᅠᇀ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11c1, "산업"); // ᄊᅠᇁ
		appendAbbreviation(abbreviations, 0x110a, 0x1160, 0x11fa, "인식"); // ᄊᅠᇺ
		appendAbbreviation(abbreviations, 0x110a, 0x1189, 0, "수요"); // ᄊᆉ
		appendAbbreviation(abbreviations, 0x110a, 0x1189, 0x11ab, "우산"); // ᄊᆉᆫ
		appendAbbreviation(abbreviations, 0x110a, 0x1189, 0x11b7, "싸움"); // ᄊᆉᆷ
		appendAbbreviation(abbreviations, 0x110a, 0x1195, 0, "우승"); // ᄊᆕ
		appendAbbreviation(abbreviations, 0x110a, 0x1198, 0, "예술가"); // ᄊᆘ
		appendAbbreviation(abbreviations, 0x110a, 0x1198, 0x11ab, "예산"); // ᄊᆘᆫ
		appendAbbreviation(abbreviations, 0x110a, 0x1198, 0x11bc, "예상"); // ᄊᆘᆼ
		appendAbbreviation(abbreviations, 0x110a, 0x119e, 0, "의사"); // ᄊᆞ
		appendAbbreviation(abbreviations, 0x110a, 0xd7b0, 0, "연습"); // ᄊힰ
		appendAbbreviation(abbreviations, 0x110a, 0xd7bf, 0, "에서 "); // ᄊힿ
		appendAbbreviation(abbreviations, 0x110a, 0xd7bf, 0x11af, "세월"); // ᄊힿᆯ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11a8, "음악"); // ᄋᅠᆨ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11ab, "어떤 "); // ᄋᅠᆫ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11ac, "인물"); // ᄋᅠᆬ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11ad, "지 않"); // ᄋᅠᆭ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11af, "일어"); // ᄋᅠᆯ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11b0, "움직"); // ᄋᅠᆰ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11b7, "음식"); // ᄋᅠᆷ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11b8, "입니다. "); // ᄋᅠᆸ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11b9, "없이 "); // ᄋᅠᆹ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11ba, "이웃"); // ᄋᅠᆺ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11bc, "여성"); // ᄋᅠᆼ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11bd, "있을 "); // ᄋᅠᆽ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11c2, "이렇"); // ᄋᅠᇂ
		appendAbbreviation(abbreviations, 0x110b, 0x1160, 0x11dd, "웃음"); // ᄋᅠᇝ
		appendAbbreviation(abbreviations, 0x110b, 0x1161, 0x11fb, "압력"); // 아ᇻ
		appendAbbreviation(abbreviations, 0x110b, 0x1175, 0x11fa, "인력"); // 이ᇺ
		appendAbbreviation(abbreviations, 0x110b, 0x117e, 0, "영국"); // ᄋᅾ
		appendAbbreviation(abbreviations, 0x110b, 0x117e, 0x11ab, "우연"); // ᄋᅾᆫ
		appendAbbreviation(abbreviations, 0x110b, 0x1189, 0, "아무"); // ᄋᆉ
		appendAbbreviation(abbreviations, 0x110b, 0x1189, 0x11af, "아울러"); // ᄋᆉᆯ
		appendAbbreviation(abbreviations, 0x110b, 0x1189, 0x11ba, "아무것"); // ᄋᆉᆺ
		appendAbbreviation(abbreviations, 0x110b, 0x1195, 0, "의문"); // ᄋᆕ
		appendAbbreviation(abbreviations, 0x110b, 0x1198, 0, "에요. "); // ᄋᆘ
		appendAbbreviation(abbreviations, 0x110b, 0x119e, 0, "아들"); // ᄋᆞ
		appendAbbreviation(abbreviations, 0x110b, 0x119e, 0x11ab, "아는 "); // ᄋᆞᆫ
		appendAbbreviation(abbreviations, 0x110b, 0x119e, 0x11bc, "않은 "); // ᄋᆞᆼ
		appendAbbreviation(abbreviations, 0x110b, 0x11a2, 0, "의원"); // ᄋᆢ
		appendAbbreviation(abbreviations, 0x110b, 0xd7b0, 0, "으며 "); // ᄋힰ
		appendAbbreviation(abbreviations, 0x110b, 0xd7b0, 0x11ab, "으면 "); // ᄋힰᆫ
		appendAbbreviation(abbreviations, 0x110b, 0xd7bb, 0, "은혜"); // ᄋힻ
		appendAbbreviation(abbreviations, 0x110b, 0xd7bc, 0, "예의"); // ᄋힼ
		appendAbbreviation(abbreviations, 0x110b, 0xd7bf, 0, "예요. "); // ᄋힿ
		appendAbbreviation(abbreviations, 0x110b, 0xd7bf, 0x11ab, "언제나 "); // ᄋힿᆫ
		appendAbbreviation(abbreviations, 0x110b, 0xd7c0, 0, "예외"); // ᄋퟀ
		appendAbbreviation(abbreviations, 0x110b, 0xd7c4, 0, "영어"); // ᄋퟄ
		appendAbbreviation(abbreviations, 0x110b, 0xd7c4, 0x11ab, "영원"); // ᄋퟄᆫ
		appendAbbreviation(abbreviations, 0x110b, 0xd7c4, 0x11bc, "영양"); // ᄋퟄᆼ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11a8, "조직"); // ᄌᅠᆨ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11a9, "직장"); // ᄌᅠᆩ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11ab, "존재"); // ᄌᅠᆫ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11ac, "진출"); // ᄌᅠᆬ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11ad, "전쟁"); // ᄌᅠᆭ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11af, "시절"); // ᄌᅠᆯ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11b0, "적절"); // ᄌᅠᆰ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11b5, "집중"); // ᄌᅠᆵ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11b7, "조금"); // ᄌᅠᆷ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11b8, "잡지"); // ᄌᅠᆸ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11ba, "저것"); // ᄌᅠᆺ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11bc, "정도"); // ᄌᅠᆼ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11c2, "좋아"); // ᄌᅠᇂ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11fa, "적인 "); // ᄌᅠᇺ
		appendAbbreviation(abbreviations, 0x110c, 0x1160, 0x11fb, "직접 "); // ᄌᅠᇻ
		appendAbbreviation(abbreviations, 0x110c, 0x1165, 0x11fb, "직접적"); // 저ᇻ
		appendAbbreviation(abbreviations, 0x110c, 0x1169, 0x11fb, "접촉"); // 조ᇻ
		appendAbbreviation(abbreviations, 0x110c, 0x1189, 0, "자주 "); // ᄌᆉ
		appendAbbreviation(abbreviations, 0x110c, 0x1189, 0x11ab, "전문가"); // ᄌᆉᆫ
		appendAbbreviation(abbreviations, 0x110c, 0x1189, 0x11af, "자율"); // ᄌᆉᆯ
		appendAbbreviation(abbreviations, 0x110c, 0x1189, 0x11bc, "주장"); // ᄌᆉᆼ
		appendAbbreviation(abbreviations, 0x110c, 0x1195, 0, "주는 "); // ᄌᆕ
		appendAbbreviation(abbreviations, 0x110c, 0x1198, 0, "자체"); // ᄌᆘ
		appendAbbreviation(abbreviations, 0x110c, 0x1198, 0x11a8, "제작"); // ᄌᆘᆨ
		appendAbbreviation(abbreviations, 0x110c, 0x119e, 0, "작은 "); // ᄌᆞ
		appendAbbreviation(abbreviations, 0x110c, 0xd7bc, 0, "좋은 "); // ᄌힼ
		appendAbbreviation(abbreviations, 0x110c, 0xd7bf, 0, "전체"); // ᄌힿ
		appendAbbreviation(abbreviations, 0x110c, 0xd7bf, 0x11a8, "전체적"); // ᄌힿᆨ
		appendAbbreviation(abbreviations, 0x110c, 0xd7c0, 0, "지혜"); // ᄌퟀ
		appendAbbreviation(abbreviations, 0x110c, 0xd7c4, 0, "어쩌면 "); // ᄌퟄ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11a8, "지역"); // ᄍᅠᆨ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11a9, "작용"); // ᄍᅠᆩ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11ab, "자연"); // ᄍᅠᆫ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11ac, "출연"); // ᄍᅠᆬ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11ad, "인정"); // ᄍᅠᆭ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11af, "일정"); // ᄍᅠᆯ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11b0, "죽음"); // ᄍᅠᆰ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11b1, "젊은"); // ᄍᅠᆱ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11b2, "졸업"); // ᄍᅠᆲ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11b5, "입장"); // ᄍᅠᆵ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11b7, "아침"); // ᄍᅠᆷ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11b8, "작업"); // ᄍᅠᆸ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11bc, "중요"); // ᄍᅠᆼ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11bd, "찾아"); // ᄍᅠᆽ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11c1, "집안"); // ᄍᅠᇁ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11fa, "직원"); // ᄍᅠᇺ
		appendAbbreviation(abbreviations, 0x110d, 0x1160, 0x11fb, "직업"); // ᄍᅠᇻ
		appendAbbreviation(abbreviations, 0x110d, 0x1169, 0x11fa, "왼쪽"); // 쪼ᇺ
		appendAbbreviation(abbreviations, 0x110d, 0x1189, 0, "아주 "); // ᄍᆉ
		appendAbbreviation(abbreviations, 0x110d, 0x1189, 0x11ab, "자원"); // ᄍᆉᆫ
		appendAbbreviation(abbreviations, 0x110d, 0x1189, 0x11ad, "위원장"); // ᄍᆉᆭ
		appendAbbreviation(abbreviations, 0x110d, 0x1189, 0x11bc, "중앙"); // ᄍᆉᆼ
		appendAbbreviation(abbreviations, 0x110d, 0x1195, 0, "주의"); // ᄍᆕ
		appendAbbreviation(abbreviations, 0x110d, 0x1198, 0x11ab, "언젠가 "); // ᄍᆘᆫ
		appendAbbreviation(abbreviations, 0x110d, 0x119e, 0, "의자"); // ᄍᆞ
		appendAbbreviation(abbreviations, 0x110d, 0x119e, 0x11af, "짧은 "); // ᄍᆞᆯ
		appendAbbreviation(abbreviations, 0x110d, 0x119e, 0x11b7, "요즘"); // ᄍᆞᆷ
		appendAbbreviation(abbreviations, 0x110d, 0x11a2, 0, "정의"); // ᄍᆢ
		appendAbbreviation(abbreviations, 0x110d, 0xd7bf, 0, "어제"); // ᄍힿ
		appendAbbreviation(abbreviations, 0x110d, 0xd7bf, 0x11ab, "언제"); // ᄍힿᆫ
		appendAbbreviation(abbreviations, 0x110d, 0xd7bf, 0x11bc, "예정"); // ᄍힿᆼ
		appendAbbreviation(abbreviations, 0x110d, 0xd7c4, 0, "여전"); // ᄍퟄ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11a8, "정책"); // ᄎᅠᆨ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11a9, "정확"); // ᄎᅠᆩ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11ab, "하지만 "); // ᄎᅠᆫ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11ac, "출신"); // ᄎᅠᆬ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11ad, "현장"); // ᄎᅠᆭ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11af, "지하철"); // ᄎᅠᆯ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11b0, "철학"); // ᄎᅠᆰ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11b5, "종합"); // ᄎᅠᆵ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11b7, "처음"); // ᄎᅠᆷ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11b8, "조합"); // ᄎᅠᆸ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11bc, "정치"); // ᄎᅠᆼ
		appendAbbreviation(abbreviations, 0x110e, 0x1160, 0x11c0, "한참"); // ᄎᅠᇀ
		appendAbbreviation(abbreviations, 0x110e, 0x1189, 0x11af, "출산"); // ᄎᆉᆯ
		appendAbbreviation(abbreviations, 0x110e, 0x1198, 0, "화제"); // ᄎᆘ
		appendAbbreviation(abbreviations, 0x110e, 0x1198, 0x11ab, "제한"); // ᄎᆘᆫ
		appendAbbreviation(abbreviations, 0x110e, 0x11a2, 0, "저희"); // ᄎᆢ
		appendAbbreviation(abbreviations, 0x110e, 0xd7be, 0, "형제"); // ᄎힾ
		appendAbbreviation(abbreviations, 0x110e, 0xd7bf, 0, "체험"); // ᄎힿ
		appendAbbreviation(abbreviations, 0x110e, 0xd7c4, 0, "전혀 "); // ᄎퟄ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11a8, "개혁"); // ᄏᅠᆨ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11ab, "환경"); // ᄏᅠᆫ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11ac, "결혼"); // ᄏᅠᆬ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11ad, "균형"); // ᄏᅠᆭ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11af, "결코 "); // ᄏᅠᆯ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11b7, "컴퓨터"); // ᄏᅠᆷ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11ba, "하겠"); // ᄏᅠᆺ
		appendAbbreviation(abbreviations, 0x110f, 0x1160, 0x11bc, "고향"); // ᄏᅠᆼ
		appendAbbreviation(abbreviations, 0x110f, 0x1198, 0, "한계"); // ᄏᆘ
		appendAbbreviation(abbreviations, 0x110f, 0x119e, 0, "한글"); // ᄏᆞ
		appendAbbreviation(abbreviations, 0x110f, 0xd7b0, 0, "경향"); // ᄏힰ
		appendAbbreviation(abbreviations, 0x110f, 0xd7bb, 0, "크게 "); // ᄏힻ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11a8, "특히 "); // ᄐᅠᆨ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11a9, "특정"); // ᄐᅠᆩ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11ab, "대한"); // ᄐᅠᆫ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11ad, "통신"); // ᄐᅠᆭ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11af, "활동"); // ᄐᅠᆯ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11b5, "통합"); // ᄐᅠᆵ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11b7, "회담"); // ᄐᅠᆷ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11b9, "틀림없"); // ᄐᅠᆹ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11bc, "행동"); // ᄐᅠᆼ
		appendAbbreviation(abbreviations, 0x1110, 0x1160, 0x11fa, "대한민국"); // ᄐᅠᇺ
		appendAbbreviation(abbreviations, 0x1110, 0x1189, 0x11ab, "한두 "); // ᄐᆉᆫ
		appendAbbreviation(abbreviations, 0x1110, 0x1198, 0, "한테 "); // ᄐᆘ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11a9, "행복"); // ᄑᅠᆩ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11ab, "북한"); // ᄑᅠᆫ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11ac, "불편"); // ᄑᅠᆬ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11ad, "형편"); // ᄑᅠᆭ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11af, "필요"); // ᄑᅠᆯ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11b7, "포함"); // ᄑᅠᆷ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11bc, "표정"); // ᄑᅠᆼ
		appendAbbreviation(abbreviations, 0x1111, 0x1160, 0x11fa, "한복"); // ᄑᅠᇺ
		appendAbbreviation(abbreviations, 0x1111, 0x1189, 0x11af, "발휘"); // ᄑᆉᆯ
		appendAbbreviation(abbreviations, 0x1111, 0xd7c4, 0, "표현"); // ᄑퟄ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11a8, "학교"); // ᄒᅠᆨ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11ab, "한국"); // ᄒᅠᆫ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11ad, "한강"); // ᄒᅠᆭ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11af, "할머니"); // ᄒᅠᆯ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11b7, "함께 "); // ᄒᅠᆷ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11b8, "합니다. "); // ᄒᅠᆸ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11bc, "항상 "); // ᄒᅠᆼ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11c2, "하얗"); // ᄒᅠᇂ
		appendAbbreviation(abbreviations, 0x1112, 0x1160, 0x11fb, "협력"); // ᄒᅠᇻ
		appendAbbreviation(abbreviations, 0x1112, 0x1198, 0, "하게 "); // ᄒᆘ
		appendAbbreviation(abbreviations, 0x1112, 0xd7c4, 0x11ab, "현저"); // ᄒퟄᆫ
		appendAbbreviation(abbreviations, 0x1115, 0x1160, 0, ", "); // ᄕᅠ
		appendAbbreviation(abbreviations, 0x1115, 0x1160, 0x11ab, "는데"); // ᄕᅠᆫ
		appendAbbreviation(abbreviations, 0x1115, 0x1160, 0x11ba, "인터넷"); // ᄕᅠᆺ
		appendAbbreviation(abbreviations, 0x1115, 0x1160, 0x11bc, "노동"); // ᄕᅠᆼ
		appendAbbreviation(abbreviations, 0x1115, 0x1161, 0, "나타"); // ᄕᅡ
		appendAbbreviation(abbreviations, 0x1115, 0x1161, 0x11bc, "노동자"); // ᄕᅡᆼ
		appendAbbreviation(abbreviations, 0x1115, 0x1162, 0, "다니"); // ᄕᅢ
		appendAbbreviation(abbreviations, 0x1115, 0x1165, 0, "더니 "); // ᄕᅥ
		appendAbbreviation(abbreviations, 0x1115, 0x1167, 0x11ab, "년대"); // ᄕᅧᆫ
		appendAbbreviation(abbreviations, 0x1115, 0x1168, 0, "는데도 "); // ᄕᅨ
		appendAbbreviation(abbreviations, 0x1115, 0x1169, 0, "또는 "); // ᄕᅩ
		appendAbbreviation(abbreviations, 0x1115, 0x1169, 0x11bc, "동네"); // ᄕᅩᆼ
		appendAbbreviation(abbreviations, 0x1115, 0x116c, 0, "되는 "); // ᄕᅬ
		appendAbbreviation(abbreviations, 0x1115, 0x1171, 0, "되는데"); // ᄕᅱ
		appendAbbreviation(abbreviations, 0x1115, 0x1173, 0, "드는 "); // ᄕᅳ
		appendAbbreviation(abbreviations, 0x1115, 0x1173, 0x11ab, "다는 "); // ᄕᅳᆫ
		appendAbbreviation(abbreviations, 0x1115, 0x1175, 0, "니다. "); // ᄕᅵ
		appendAbbreviation(abbreviations, 0x1115, 0x119e, 0, "는다. "); // ᄕᆞ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11a8, "기독교"); // ᄗᅠᆨ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11ab, "가운데"); // ᄗᅠᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11ad, "그동안"); // ᄗᅠᆭ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11af, "갈등"); // ᄗᅠᆯ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11b0, "까닭"); // ᄗᅠᆰ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11b7, "감독"); // ᄗᅠᆷ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11ba, "되겠"); // ᄗᅠᆺ
		appendAbbreviation(abbreviations, 0x1117, 0x1160, 0x11bc, "공동"); // ᄗᅠᆼ
		appendAbbreviation(abbreviations, 0x1117, 0x1161, 0, "다가"); // ᄗᅡ
		appendAbbreviation(abbreviations, 0x1117, 0x1161, 0x11ab, "간단"); // ᄗᅡᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x1161, 0x11af, "달걀"); // ᄗᅡᆯ
		appendAbbreviation(abbreviations, 0x1117, 0x1161, 0x11b7, "감동"); // ᄗᅡᆷ
		appendAbbreviation(abbreviations, 0x1117, 0x1162, 0, "기대"); // ᄗᅢ
		appendAbbreviation(abbreviations, 0x1117, 0x1165, 0, "거대"); // ᄗᅥ
		appendAbbreviation(abbreviations, 0x1117, 0x1165, 0x11ab, "던가? "); // ᄗᅥᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x1165, 0x11ba, "것도 "); // ᄗᅥᆺ
		appendAbbreviation(abbreviations, 0x1117, 0x1166, 0x11ab, "단계"); // ᄗᅦᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x1168, 0, "계단"); // ᄗᅨ
		appendAbbreviation(abbreviations, 0x1117, 0x1169, 0, "검토"); // ᄗᅩ
		appendAbbreviation(abbreviations, 0x1117, 0x1169, 0x11bc, "고통"); // ᄗᅩᆼ
		appendAbbreviation(abbreviations, 0x1117, 0x116a, 0, "다고"); // ᄗᅪ
		appendAbbreviation(abbreviations, 0x1117, 0x116b, 0, "고대"); // ᄗᅫ
		appendAbbreviation(abbreviations, 0x1117, 0x116c, 0, "기도"); // ᄗᅬ
		appendAbbreviation(abbreviations, 0x1117, 0x116d, 0x11bc, "교통"); // ᄗᅭᆼ
		appendAbbreviation(abbreviations, 0x1117, 0x116e, 0, "구두"); // ᄗᅮ
		appendAbbreviation(abbreviations, 0x1117, 0x116e, 0x11ab, "군대"); // ᄗᅮᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x116f, 0x11ab, "더군. "); // ᄗᅯᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x1173, 0, "그대"); // ᄗᅳ
		appendAbbreviation(abbreviations, 0x1117, 0x1173, 0x11a8, "가득 "); // ᄗᅳᆨ
		appendAbbreviation(abbreviations, 0x1117, 0x1173, 0x11ab, "근데 "); // ᄗᅳᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x1173, 0x11bc, "고등"); // ᄗᅳᆼ
		appendAbbreviation(abbreviations, 0x1117, 0x1175, 0, "기다"); // ᄗᅵ
		appendAbbreviation(abbreviations, 0x1117, 0x1175, 0x11bc, "동기"); // ᄗᅵᆼ
		appendAbbreviation(abbreviations, 0x1117, 0x1189, 0, "더구나 "); // ᄗᆉ
		appendAbbreviation(abbreviations, 0x1117, 0x1198, 0, "게다가 "); // ᄗᆘ
		appendAbbreviation(abbreviations, 0x1117, 0x119e, 0, "그때"); // ᄗᆞ
		appendAbbreviation(abbreviations, 0x1117, 0x119e, 0x11ab, "든가"); // ᄗᆞᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x11a2, 0, "뜨거운 "); // ᄗᆢ
		appendAbbreviation(abbreviations, 0x1117, 0x11a2, 0x11ab, "거든"); // ᄗᆢᆫ
		appendAbbreviation(abbreviations, 0x1117, 0x11a2, 0x11b8, "거듭"); // ᄗᆢᆸ
		appendAbbreviation(abbreviations, 0x1117, 0xd7bc, 0, "코드"); // ᄗힼ
		appendAbbreviation(abbreviations, 0x111a, 0x1160, 0x11ab, "훈련"); // ᄚᅠᆫ
		appendAbbreviation(abbreviations, 0x111a, 0x1160, 0x11b7, "흐름"); // ᄚᅠᆷ
		appendAbbreviation(abbreviations, 0x111a, 0x1160, 0x11bc, "훌륭"); // ᄚᅠᆼ
		appendAbbreviation(abbreviations, 0x111a, 0x1161, 0, "하루"); // ᄚᅡ
		appendAbbreviation(abbreviations, 0x111a, 0x1161, 0x11bc, "호랑이"); // ᄚᅡᆼ
		appendAbbreviation(abbreviations, 0x111a, 0x1162, 0x11b8, "합리"); // ᄚᅢᆸ
		appendAbbreviation(abbreviations, 0x111a, 0x1165, 0, "허리"); // ᄚᅥ
		appendAbbreviation(abbreviations, 0x111a, 0x116a, 0, "화려"); // ᄚᅪ
		appendAbbreviation(abbreviations, 0x111a, 0x1175, 0, "차라리 "); // ᄚᅵ
		appendAbbreviation(abbreviations, 0x111b, 0x1160, 0x11a8, "노력"); // ᄛᅠᆨ
		appendAbbreviation(abbreviations, 0x111b, 0x1160, 0x11ab, "논리"); // ᄛᅠᆫ
		appendAbbreviation(abbreviations, 0x111b, 0x1160, 0x11af, "널리 "); // ᄛᅠᆯ
		appendAbbreviation(abbreviations, 0x111b, 0x1160, 0x11b7, "나름"); // ᄛᅠᆷ
		appendAbbreviation(abbreviations, 0x111b, 0x1160, 0x11bc, "능력"); // ᄛᅠᆼ
		appendAbbreviation(abbreviations, 0x111b, 0x1161, 0, "나라"); // ᄛᅡ
		appendAbbreviation(abbreviations, 0x111b, 0x1162, 0, "내려"); // ᄛᅢ
		appendAbbreviation(abbreviations, 0x111b, 0x1169, 0, "노래"); // ᄛᅩ
		appendAbbreviation(abbreviations, 0x111b, 0x1173, 0, "르는 "); // ᄛᅳ
		appendAbbreviation(abbreviations, 0x111b, 0x1174, 0, "리는 "); // ᄛᅴ
		appendAbbreviation(abbreviations, 0x111b, 0x1175, 0, "그러니"); // ᄛᅵ
		appendAbbreviation(abbreviations, 0x111c, 0x1160, 0x11a8, "목표"); // ᄜᅠᆨ
		appendAbbreviation(abbreviations, 0x111c, 0x1160, 0x11ab, "반면"); // ᄜᅠᆫ
		appendAbbreviation(abbreviations, 0x111c, 0x1160, 0x11af, "비밀"); // ᄜᅠᆯ
		appendAbbreviation(abbreviations, 0x111c, 0x1160, 0x11b7, "부모님"); // ᄜᅠᆷ
		appendAbbreviation(abbreviations, 0x111c, 0x1160, 0x11bc, "분명"); // ᄜᅠᆼ
		appendAbbreviation(abbreviations, 0x111c, 0x1161, 0x11a8, "박물관"); // ᄜᅡᆨ
		appendAbbreviation(abbreviations, 0x111c, 0x1161, 0x11af, "불만"); // ᄜᅡᆯ
		appendAbbreviation(abbreviations, 0x111c, 0x1161, 0x11bc, "방문"); // ᄜᅡᆼ
		appendAbbreviation(abbreviations, 0x111c, 0x1162, 0, "판매"); // ᄜᅢ
		appendAbbreviation(abbreviations, 0x111c, 0x116d, 0, "표면"); // ᄜᅭ
		appendAbbreviation(abbreviations, 0x111c, 0x116e, 0, "부모"); // ᄜᅮ
		appendAbbreviation(abbreviations, 0x111c, 0x116e, 0x11ab, "부문"); // ᄜᅮᆫ
		appendAbbreviation(abbreviations, 0x111c, 0x1175, 0x11bc, "분명히 "); // ᄜᅵᆼ
		appendAbbreviation(abbreviations, 0x111d, 0x1160, 0, "! "); // ᄝᅠ
		appendAbbreviation(abbreviations, 0x111d, 0x1160, 0x11ab, "논문"); // ᄝᅠᆫ
		appendAbbreviation(abbreviations, 0x111d, 0x1160, 0x11af, "눈물"); // ᄝᅠᆯ
		appendAbbreviation(abbreviations, 0x111d, 0x1160, 0x11bc, "농민"); // ᄝᅠᆼ
		appendAbbreviation(abbreviations, 0x111d, 0x1161, 0, "나마 "); // ᄝᅡ
		appendAbbreviation(abbreviations, 0x111d, 0x1161, 0x11ab, "농산물"); // ᄝᅡᆫ
		appendAbbreviation(abbreviations, 0x111d, 0x1165, 0, "너무 "); // ᄝᅥ
		appendAbbreviation(abbreviations, 0x111d, 0x1166, 0, "만나게 "); // ᄝᅦ
		appendAbbreviation(abbreviations, 0x111d, 0x116e, 0, "주머니"); // ᄝᅮ
		appendAbbreviation(abbreviations, 0x111d, 0x1173, 0, "모르는 "); // ᄝᅳ
		appendAbbreviation(abbreviations, 0x111d, 0x1173, 0x11ab, "맞는 "); // ᄝᅳᆫ
		appendAbbreviation(abbreviations, 0x111d, 0x1173, 0x11af, "마늘"); // ᄝᅳᆯ
		appendAbbreviation(abbreviations, 0x111d, 0x1175, 0, "나머지"); // ᄝᅵ
		appendAbbreviation(abbreviations, 0x111d, 0x1189, 0, "나무"); // ᄝᆉ
		appendAbbreviation(abbreviations, 0x111e, 0x1160, 0x11a8, "극복"); // ᄞᅠᆨ
		appendAbbreviation(abbreviations, 0x111e, 0x1160, 0x11ab, "분위기"); // ᄞᅠᆫ
		appendAbbreviation(abbreviations, 0x111e, 0x1160, 0x11ad, "평균"); // ᄞᅠᆭ
		appendAbbreviation(abbreviations, 0x111e, 0x1160, 0x11af, "개발"); // ᄞᅠᆯ
		appendAbbreviation(abbreviations, 0x111e, 0x1160, 0x11bc, "공부"); // ᄞᅠᆼ
		appendAbbreviation(abbreviations, 0x111e, 0x1160, 0x11c0, "바깥"); // ᄞᅠᇀ
		appendAbbreviation(abbreviations, 0x111e, 0x1161, 0x11ab, "반갑"); // ᄞᅡᆫ
		appendAbbreviation(abbreviations, 0x111e, 0x1161, 0x11af, "발견"); // ᄞᅡᆯ
		appendAbbreviation(abbreviations, 0x111e, 0x1161, 0x11bc, "가방"); // ᄞᅡᆼ
		appendAbbreviation(abbreviations, 0x111e, 0x1162, 0, "배경"); // ᄞᅢ
		appendAbbreviation(abbreviations, 0x111e, 0x1162, 0x11bc, "개방"); // ᄞᅢᆼ
		appendAbbreviation(abbreviations, 0x111e, 0x1165, 0, "커피"); // ᄞᅥ
		appendAbbreviation(abbreviations, 0x111e, 0x1166, 0, "쁘게 "); // ᄞᅦ
		appendAbbreviation(abbreviations, 0x111e, 0x1167, 0x11af, "판결"); // ᄞᅧᆯ
		appendAbbreviation(abbreviations, 0x111e, 0x1167, 0x11bc, "평가"); // ᄞᅧᆼ
		appendAbbreviation(abbreviations, 0x111e, 0x1169, 0, "보고"); // ᄞᅩ
		appendAbbreviation(abbreviations, 0x111e, 0x1169, 0x11a8, "본격적"); // ᄞᅩᆨ
		appendAbbreviation(abbreviations, 0x111e, 0x1169, 0x11ab, "기본"); // ᄞᅩᆫ
		appendAbbreviation(abbreviations, 0x111e, 0x1169, 0x11bc, "공포"); // ᄞᅩᆼ
		appendAbbreviation(abbreviations, 0x111e, 0x116a, 0, "불과"); // ᄞᅪ
		appendAbbreviation(abbreviations, 0x111e, 0x116a, 0x11ab, "보관"); // ᄞᅪᆫ
		appendAbbreviation(abbreviations, 0x111e, 0x116c, 0, "포기"); // ᄞᅬ
		appendAbbreviation(abbreviations, 0x111e, 0x116d, 0, "비교"); // ᄞᅭ
		appendAbbreviation(abbreviations, 0x111e, 0x116d, 0x11a8, "비교적"); // ᄞᅭᆨ
		appendAbbreviation(abbreviations, 0x111e, 0x116e, 0, "불구"); // ᄞᅮ
		appendAbbreviation(abbreviations, 0x111e, 0x116e, 0x11ab, "구분"); // ᄞᅮᆫ
		appendAbbreviation(abbreviations, 0x111e, 0x116e, 0x11af, "불교"); // ᄞᅮᆯ
		appendAbbreviation(abbreviations, 0x111e, 0x116f, 0, "거부"); // ᄞᅯ
		appendAbbreviation(abbreviations, 0x111e, 0x1173, 0, "프게 "); // ᄞᅳ
		appendAbbreviation(abbreviations, 0x111e, 0x1173, 0x11a8, "근본적"); // ᄞᅳᆨ
		appendAbbreviation(abbreviations, 0x111e, 0x1173, 0x11b7, "금방 "); // ᄞᅳᆷ
		appendAbbreviation(abbreviations, 0x111e, 0x1174, 0, "기쁨"); // ᄞᅴ
		appendAbbreviation(abbreviations, 0x111e, 0x1174, 0x11a8, "비극"); // ᄞᅴᆨ
		appendAbbreviation(abbreviations, 0x111e, 0x1175, 0, "기분"); // ᄞᅵ
		appendAbbreviation(abbreviations, 0x111e, 0x1175, 0x11a8, "기본적"); // ᄞᅵᆨ
		appendAbbreviation(abbreviations, 0x111e, 0x1175, 0x11bc, "비행기"); // ᄞᅵᆼ
		appendAbbreviation(abbreviations, 0x111e, 0x1189, 0, "간부"); // ᄞᆉ
		appendAbbreviation(abbreviations, 0x111e, 0x1195, 0, "부끄"); // ᄞᆕ
		appendAbbreviation(abbreviations, 0x111e, 0x1198, 0, "카페"); // ᄞᆘ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0, ". "); // ᄠᅠ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0x11a8, "특별"); // ᄠᅠᆨ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0x11ab, "대부분"); // ᄠᅠᆫ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0x11af, "발달"); // ᄠᅠᆯ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0x11b7, "담배"); // ᄠᅠᆷ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0x11bc, "바탕"); // ᄠᅠᆼ
		appendAbbreviation(abbreviations, 0x1120, 0x1160, 0x11bd, "부딪"); // ᄠᅠᆽ
		appendAbbreviation(abbreviations, 0x1120, 0x1161, 0, "다. "); // ᄠᅡ
		appendAbbreviation(abbreviations, 0x1120, 0x1161, 0x11a8, "바닥"); // ᄠᅡᆨ
		appendAbbreviation(abbreviations, 0x1120, 0x1161, 0x11ab, "판단"); // ᄠᅡᆫ
		appendAbbreviation(abbreviations, 0x1120, 0x1162, 0, "부대"); // ᄠᅢ
		appendAbbreviation(abbreviations, 0x1120, 0x1162, 0x11ab, "반대"); // ᄠᅢᆫ
		appendAbbreviation(abbreviations, 0x1120, 0x1162, 0x11bc, "상대방"); // ᄠᅢᆼ
		appendAbbreviation(abbreviations, 0x1120, 0x1165, 0, "퍼센트"); // ᄠᅥ
		appendAbbreviation(abbreviations, 0x1120, 0x1169, 0, "보다 "); // ᄠᅩ
		appendAbbreviation(abbreviations, 0x1120, 0x1169, 0x11ad, "부동산"); // ᄠᅩᆭ
		appendAbbreviation(abbreviations, 0x1120, 0x1169, 0x11bc, "보통"); // ᄠᅩᆼ
		appendAbbreviation(abbreviations, 0x1120, 0x116a, 0, "도 불구하고 "); // ᄠᅪ
		appendAbbreviation(abbreviations, 0x1120, 0x116a, 0x11ab, "반도"); // ᄠᅪᆫ
		appendAbbreviation(abbreviations, 0x1120, 0x116c, 0, "비디오"); // ᄠᅬ
		appendAbbreviation(abbreviations, 0x1120, 0x116d, 0, "대표"); // ᄠᅭ
		appendAbbreviation(abbreviations, 0x1120, 0x116d, 0x11a8, "대표적"); // ᄠᅭᆨ
		appendAbbreviation(abbreviations, 0x1120, 0x116e, 0, "부터 "); // ᄠᅮ
		appendAbbreviation(abbreviations, 0x1120, 0x1172, 0, "부도"); // ᄠᅲ
		appendAbbreviation(abbreviations, 0x1120, 0x1173, 0, "부드"); // ᄠᅳ
		appendAbbreviation(abbreviations, 0x1120, 0x1173, 0x11ab, "반드시 "); // ᄠᅳᆫ
		appendAbbreviation(abbreviations, 0x1120, 0x1174, 0, "티브이"); // ᄠᅴ
		appendAbbreviation(abbreviations, 0x1120, 0x1175, 0, "대비"); // ᄠᅵ
		appendAbbreviation(abbreviations, 0x1120, 0x1189, 0, "부담"); // ᄠᆉ
		appendAbbreviation(abbreviations, 0x1120, 0x1189, 0x11a8, "부탁"); // ᄠᆉᆨ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11a8, "새벽"); // ᄡᅠᆨ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11a9, "방송국"); // ᄡᅠᆩ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11ab, "부산"); // ᄡᅠᆫ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11ac, "신발"); // ᄡᅠᆬ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11af, "살펴"); // ᄡᅠᆯ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11b7, "상품"); // ᄡᅠᆷ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11b9, " 수밖에 없"); // ᄡᅠᆹ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11ba, "비슷"); // ᄡᅠᆺ
		appendAbbreviation(abbreviations, 0x1121, 0x1160, 0x11bc, "방식"); // ᄡᅠᆼ
		appendAbbreviation(abbreviations, 0x1121, 0x1161, 0x11a8, "박사"); // ᄡᅡᆨ
		appendAbbreviation(abbreviations, 0x1121, 0x1161, 0x11af, "발생"); // ᄡᅡᆯ
		appendAbbreviation(abbreviations, 0x1121, 0x1161, 0x11bc, "방송"); // ᄡᅡᆼ
		appendAbbreviation(abbreviations, 0x1121, 0x1162, 0x11a8, "백성"); // ᄡᅢᆨ
		appendAbbreviation(abbreviations, 0x1121, 0x1162, 0x11bc, "평생"); // ᄡᅢᆼ
		appendAbbreviation(abbreviations, 0x1121, 0x1165, 0x11ab, "선배"); // ᄡᅥᆫ
		appendAbbreviation(abbreviations, 0x1121, 0x1165, 0x11af, "벌써 "); // ᄡᅥᆯ
		appendAbbreviation(abbreviations, 0x1121, 0x1167, 0x11bc, "평소"); // ᄡᅧᆼ
		appendAbbreviation(abbreviations, 0x1121, 0x1169, 0, "소비"); // ᄡᅩ
		appendAbbreviation(abbreviations, 0x1121, 0x116a, 0, "소비자"); // ᄡᅪ
		appendAbbreviation(abbreviations, 0x1121, 0x116c, 0, "비로소 "); // ᄡᅬ
		appendAbbreviation(abbreviations, 0x1121, 0x116e, 0, "부상"); // ᄡᅮ
		appendAbbreviation(abbreviations, 0x1121, 0x116e, 0x11ab, "분석"); // ᄡᅮᆫ
		appendAbbreviation(abbreviations, 0x1121, 0x1173, 0, "스포츠"); // ᄡᅳ
		appendAbbreviation(abbreviations, 0x1121, 0x1174, 0, "서비스"); // ᄡᅴ
		appendAbbreviation(abbreviations, 0x1121, 0x1175, 0, "신부"); // ᄡᅵ
		appendAbbreviation(abbreviations, 0x1121, 0x1175, 0x11a8, "식품"); // ᄡᅵᆨ
		appendAbbreviation(abbreviations, 0x1121, 0x1175, 0x11ab, "신분"); // ᄡᅵᆫ
		appendAbbreviation(abbreviations, 0x1121, 0x1175, 0x11af, "실패"); // ᄡᅵᆯ
		appendAbbreviation(abbreviations, 0x1121, 0x11a2, 0, "버스"); // ᄡᆢ
		appendAbbreviation(abbreviations, 0x1121, 0xd7bf, 0, "소프트웨어"); // ᄡힿ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11a8, "작품"); // ᄧᅠᆨ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11ab, "주변"); // ᄧᅠᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11ad, "충분"); // ᄧᅠᆭ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11af, "발전"); // ᄧᅠᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11b7, "방침"); // ᄧᅠᆷ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11b8, "법칙"); // ᄧᅠᆸ
		appendAbbreviation(abbreviations, 0x1127, 0x1160, 0x11bc, "정부"); // ᄧᅠᆼ
		appendAbbreviation(abbreviations, 0x1127, 0x1161, 0, "아버지"); // ᄧᅡ
		appendAbbreviation(abbreviations, 0x1127, 0x1161, 0x11ab, "자판"); // ᄧᅡᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x1161, 0x11ad, "반장"); // ᄧᅡᆭ
		appendAbbreviation(abbreviations, 0x1127, 0x1161, 0x11af, "할아버지"); // ᄧᅡᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x1161, 0x11b8, "복잡"); // ᄧᅡᆸ
		appendAbbreviation(abbreviations, 0x1127, 0x1161, 0x11bc, "지방"); // ᄧᅡᆼ
		appendAbbreviation(abbreviations, 0x1127, 0x1162, 0, "지배"); // ᄧᅢ
		appendAbbreviation(abbreviations, 0x1127, 0x1162, 0x11a8, "백제"); // ᄧᅢᆨ
		appendAbbreviation(abbreviations, 0x1127, 0x1162, 0x11ab, "재판"); // ᄧᅢᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x1162, 0x11af, "필자"); // ᄧᅢᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x1165, 0, "부처"); // ᄧᅥ
		appendAbbreviation(abbreviations, 0x1127, 0x1165, 0x11ab, "번째"); // ᄧᅥᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x1165, 0x11ac, "일반적"); // ᄧᅥᆬ
		appendAbbreviation(abbreviations, 0x1127, 0x1165, 0x11af, "처벌"); // ᄧᅥᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x1165, 0x11b7, "백화점"); // ᄧᅥᆷ
		appendAbbreviation(abbreviations, 0x1127, 0x1165, 0x11bc, "정비"); // ᄧᅥᆼ
		appendAbbreviation(abbreviations, 0x1127, 0x1166, 0, "제품"); // ᄧᅦ
		appendAbbreviation(abbreviations, 0x1127, 0x1167, 0x11ab, "편지"); // ᄧᅧᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x1167, 0x11af, "차별"); // ᄧᅧᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x1169, 0, "정보"); // ᄧᅩ
		appendAbbreviation(abbreviations, 0x1127, 0x1169, 0x11a8, "북쪽"); // ᄧᅩᆨ
		appendAbbreviation(abbreviations, 0x1127, 0x1169, 0x11ab, "자본"); // ᄧᅩᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x116a, 0, "화장품"); // ᄧᅪ
		appendAbbreviation(abbreviations, 0x1127, 0x116a, 0x11bc, "보장"); // ᄧᅪᆼ
		appendAbbreviation(abbreviations, 0x1127, 0x116c, 0, "범죄"); // ᄧᅬ
		appendAbbreviation(abbreviations, 0x1127, 0x116d, 0x11ab, "전파"); // ᄧᅭᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x116d, 0x11af, "재벌"); // ᄧᅭᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x116e, 0, "주부"); // ᄧᅮ
		appendAbbreviation(abbreviations, 0x1127, 0x116e, 0x11a8, "부족"); // ᄧᅮᆨ
		appendAbbreviation(abbreviations, 0x1127, 0x116e, 0x11ab, "준비"); // ᄧᅮᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x116e, 0x11af, "출발"); // ᄧᅮᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x116f, 0, "부정"); // ᄧᅯ
		appendAbbreviation(abbreviations, 0x1127, 0x116f, 0x11ab, "전부"); // ᄧᅯᆫ
		appendAbbreviation(abbreviations, 0x1127, 0x1175, 0, "바지"); // ᄧᅵ
		appendAbbreviation(abbreviations, 0x1127, 0x1175, 0x11ad, "충분히 "); // ᄧᅵᆭ
		appendAbbreviation(abbreviations, 0x1127, 0x1175, 0x11af, "본질"); // ᄧᅵᆯ
		appendAbbreviation(abbreviations, 0x1127, 0x1189, 0, "부자"); // ᄧᆉ
		appendAbbreviation(abbreviations, 0x1127, 0x1189, 0x11bc, "부장"); // ᄧᆉᆼ
		appendAbbreviation(abbreviations, 0x112b, 0x1160, 0x11af, "비닐"); // ᄫᅠᆯ
		appendAbbreviation(abbreviations, 0x112b, 0x1160, 0x11b7, "남편"); // ᄫᅠᆷ
		appendAbbreviation(abbreviations, 0x112b, 0x1160, 0x11bc, "농부"); // ᄫᅠᆼ
		appendAbbreviation(abbreviations, 0x112b, 0x1161, 0, "받는 "); // ᄫᅡ
		appendAbbreviation(abbreviations, 0x112b, 0x1161, 0x11ab, "비난"); // ᄫᅡᆫ
		appendAbbreviation(abbreviations, 0x112b, 0x1161, 0x11b7, "남부"); // ᄫᅡᆷ
		appendAbbreviation(abbreviations, 0x112b, 0x1162, 0, "보내"); // ᄫᅢ
		appendAbbreviation(abbreviations, 0x112b, 0x1162, 0x11ab, "보낸 "); // ᄫᅢᆫ
		appendAbbreviation(abbreviations, 0x112b, 0x1169, 0, "보는 "); // ᄫᅩ
		appendAbbreviation(abbreviations, 0x112b, 0x116e, 0, "내부"); // ᄫᅮ
		appendAbbreviation(abbreviations, 0x112b, 0x1173, 0x11af, "바늘"); // ᄫᅳᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11a8, "생각"); // ᄭᅠᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11ab, "시간"); // ᄭᅠᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11ad, "공산"); // ᄭᅠᆭ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11af, "기술"); // ᄭᅠᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11b0, "삼국"); // ᄭᅠᆰ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11b7, "관심"); // ᄭᅠᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11b8, "쉽게 "); // ᄭᅠᆸ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11b9, " 수가 없"); // ᄭᅠᆹ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11ba, " 수가 있"); // ᄭᅠᆺ
		appendAbbreviation(abbreviations, 0x112d, 0x1160, 0x11bc, "성격"); // ᄭᅠᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x1161, 0, "기사"); // ᄭᅡ
		appendAbbreviation(abbreviations, 0x112d, 0x1161, 0x11ab, "사건"); // ᄭᅡᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1161, 0x11af, "색깔"); // ᄭᅡᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1161, 0x11b7, "감사"); // ᄭᅡᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x1161, 0x11bc, "상관"); // ᄭᅡᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x1162, 0, "새끼"); // ᄭᅢ
		appendAbbreviation(abbreviations, 0x112d, 0x1162, 0x11a8, "시각"); // ᄭᅢᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x1162, 0x11ab, "개선"); // ᄭᅢᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1162, 0x11bc, "기상"); // ᄭᅢᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x1165, 0, "고서 "); // ᄭᅥ
		appendAbbreviation(abbreviations, 0x112d, 0x1165, 0x11a8, "구석"); // ᄭᅥᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x1165, 0x11ab, "선거"); // ᄭᅥᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1165, 0x11af, "건설"); // ᄭᅥᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1165, 0x11b7, "검사"); // ᄭᅥᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x1165, 0x11bc, "성경"); // ᄭᅥᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x1166, 0, "세계"); // ᄭᅦ
		appendAbbreviation(abbreviations, 0x112d, 0x1166, 0x11a8, "세계적"); // ᄭᅦᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x1166, 0x11b7, "세금"); // ᄭᅦᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x1167, 0x11af, "결심"); // ᄭᅧᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1167, 0x11bc, "경상"); // ᄭᅧᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x1168, 0, "시계"); // ᄭᅨ
		appendAbbreviation(abbreviations, 0x112d, 0x1168, 0x11ab, "계산"); // ᄭᅨᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1169, 0, "사고"); // ᄭᅩ
		appendAbbreviation(abbreviations, 0x112d, 0x1169, 0x11a8, "계속"); // ᄭᅩᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x1169, 0x11ab, "손가락"); // ᄭᅩᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1169, 0x11af, "시골"); // ᄭᅩᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1169, 0x11b7, "소금"); // ᄭᅩᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x1169, 0x11bc, "성공"); // ᄭᅩᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x116a, 0, "사과"); // ᄭᅪ
		appendAbbreviation(abbreviations, 0x112d, 0x116a, 0x11bc, "공사"); // ᄭᅪᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x116b, 0, "소개"); // ᄭᅫ
		appendAbbreviation(abbreviations, 0x112d, 0x116b, 0x11bc, "고생"); // ᄭᅫᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x116c, 0x11a8, "공식"); // ᄭᅬᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x116c, 0x11ab, "신고"); // ᄭᅬᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x116d, 0, "교사"); // ᄭᅭ
		appendAbbreviation(abbreviations, 0x112d, 0x116d, 0x11bc, "개성"); // ᄭᅭᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x116e, 0, "교수"); // ᄭᅮ
		appendAbbreviation(abbreviations, 0x112d, 0x116e, 0x11a8, "구속"); // ᄭᅮᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x116e, 0x11ab, "순간"); // ᄭᅮᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x116e, 0x11bc, "구성"); // ᄭᅮᆼ
		appendAbbreviation(abbreviations, 0x112d, 0x116f, 0, "서구"); // ᄭᅯ
		appendAbbreviation(abbreviations, 0x112d, 0x1171, 0, "세기"); // ᄭᅱ
		appendAbbreviation(abbreviations, 0x112d, 0x1172, 0, "수고"); // ᄭᅲ
		appendAbbreviation(abbreviations, 0x112d, 0x1173, 0, "쓰레기"); // ᄭᅳ
		appendAbbreviation(abbreviations, 0x112d, 0x1173, 0x11af, "글쎄"); // ᄭᅳᆯ
		appendAbbreviation(abbreviations, 0x112d, 0x1173, 0x11b7, "가슴"); // ᄭᅳᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x1173, 0x11b8, "습관"); // ᄭᅳᆸ
		appendAbbreviation(abbreviations, 0x112d, 0x1175, 0, "시기"); // ᄭᅵ
		appendAbbreviation(abbreviations, 0x112d, 0x1175, 0x11a8, "식구"); // ᄭᅵᆨ
		appendAbbreviation(abbreviations, 0x112d, 0x1175, 0x11ab, "신경"); // ᄭᅵᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x1175, 0x11b7, "심각"); // ᄭᅵᆷ
		appendAbbreviation(abbreviations, 0x112d, 0x117e, 0, "연구소"); // ᄭᅾ
		appendAbbreviation(abbreviations, 0x112d, 0x1189, 0, "가수"); // ᄭᆉ
		appendAbbreviation(abbreviations, 0x112d, 0x1189, 0x11ab, "군사"); // ᄭᆉᆫ
		appendAbbreviation(abbreviations, 0x112d, 0x119e, 0, "가스"); // ᄭᆞ
		appendAbbreviation(abbreviations, 0x112d, 0xd7bf, 0, "에게서 "); // ᄭힿ
		appendAbbreviation(abbreviations, 0x112e, 0x1160, 0x11ab, "아나운서"); // ᄮᅠᆫ
		appendAbbreviation(abbreviations, 0x112e, 0x1160, 0x11af, "날씨"); // ᄮᅠᆯ
		appendAbbreviation(abbreviations, 0x112e, 0x1160, 0x11b7, "선생님"); // ᄮᅠᆷ
		appendAbbreviation(abbreviations, 0x112e, 0x1160, 0x11b9, " 수는 없"); // ᄮᅠᆹ
		appendAbbreviation(abbreviations, 0x112e, 0x1160, 0x11ba, " 수는 있"); // ᄮᅠᆺ
		appendAbbreviation(abbreviations, 0x112e, 0x1160, 0x11bc, "가능성"); // ᄮᅠᆼ
		appendAbbreviation(abbreviations, 0x112e, 0x1161, 0, "사내"); // ᄮᅡ
		appendAbbreviation(abbreviations, 0x112e, 0x1162, 0, "낚시"); // ᄮᅢ
		appendAbbreviation(abbreviations, 0x112e, 0x1162, 0x11b7, "냄새"); // ᄮᅢᆷ
		appendAbbreviation(abbreviations, 0x112e, 0x1167, 0, "소녀"); // ᄮᅧ
		appendAbbreviation(abbreviations, 0x112e, 0x1167, 0x11ab, "소년"); // ᄮᅧᆫ
		appendAbbreviation(abbreviations, 0x112e, 0x1169, 0x11ab, "손님"); // ᄮᅩᆫ
		appendAbbreviation(abbreviations, 0x112e, 0x1169, 0x11bc, "농사"); // ᄮᅩᆼ
		appendAbbreviation(abbreviations, 0x112e, 0x1172, 0, "뉴스"); // ᄮᅲ
		appendAbbreviation(abbreviations, 0x112e, 0x1173, 0, "쓰는 "); // ᄮᅳ
		appendAbbreviation(abbreviations, 0x112e, 0x1174, 0, "시는 "); // ᄮᅴ
		appendAbbreviation(abbreviations, 0x112e, 0x1175, 0, "시내"); // ᄮᅵ
		appendAbbreviation(abbreviations, 0x112e, 0x1175, 0x11af, "실내"); // ᄮᅵᆯ
		appendAbbreviation(abbreviations, 0x112f, 0x1160, 0, "? "); // ᄯᅠ
		appendAbbreviation(abbreviations, 0x112f, 0x1160, 0x11a8, "세력"); // ᄯᅠᆨ
		appendAbbreviation(abbreviations, 0x112f, 0x1160, 0x11ab, "신라"); // ᄯᅠᆫ
		appendAbbreviation(abbreviations, 0x112f, 0x1160, 0x11b8, "스럽"); // ᄯᅠᆸ
		appendAbbreviation(abbreviations, 0x112f, 0x1160, 0x11bc, "사랑"); // ᄯᅠᆼ
		appendAbbreviation(abbreviations, 0x112f, 0x1161, 0, "사라"); // ᄯᅡ
		appendAbbreviation(abbreviations, 0x112f, 0x1162, 0, "새로 "); // ᄯᅢ
		appendAbbreviation(abbreviations, 0x112f, 0x1162, 0x11ab, "새로운 "); // ᄯᅢᆫ
		appendAbbreviation(abbreviations, 0x112f, 0x1165, 0, "로써 "); // ᄯᅥ
		appendAbbreviation(abbreviations, 0x112f, 0x1165, 0x11bc, "성령"); // ᄯᅥᆼ
		appendAbbreviation(abbreviations, 0x112f, 0x1167, 0x11ab, "소련"); // ᄯᅧᆫ
		appendAbbreviation(abbreviations, 0x112f, 0x1168, 0, "사례"); // ᄯᅨ
		appendAbbreviation(abbreviations, 0x112f, 0x1169, 0, "로서 "); // ᄯᅩ
		appendAbbreviation(abbreviations, 0x112f, 0x116e, 0, "수록 "); // ᄯᅮ
		appendAbbreviation(abbreviations, 0x112f, 0x116f, 0, "서로 "); // ᄯᅯ
		appendAbbreviation(abbreviations, 0x112f, 0x1171, 0, "수리"); // ᄯᅱ
		appendAbbreviation(abbreviations, 0x112f, 0x1173, 0, "스스로"); // ᄯᅳ
		appendAbbreviation(abbreviations, 0x112f, 0x1173, 0x11bc, "승리"); // ᄯᅳᆼ
		appendAbbreviation(abbreviations, 0x112f, 0x1175, 0, "소리"); // ᄯᅵ
		appendAbbreviation(abbreviations, 0x112f, 0x1175, 0x11a8, "식량"); // ᄯᅵᆨ
		appendAbbreviation(abbreviations, 0x112f, 0x1175, 0x11b7, "심리"); // ᄯᅵᆷ
		appendAbbreviation(abbreviations, 0x112f, 0x11a2, 0, "스러"); // ᄯᆢ
		appendAbbreviation(abbreviations, 0x112f, 0x11a2, 0x11ab, "스러운 "); // ᄯᆢᆫ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11a8, "목소리"); // ᄱᅠᆨ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11ab, "무슨 "); // ᄱᅠᆫ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11ac, "선물"); // ᄱᅠᆬ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11af, "말씀"); // ᄱᅠᆯ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11b0, "목숨"); // ᄱᅠᆰ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11b8, "모습"); // ᄱᅠᆸ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11ba, " 수만 있"); // ᄱᅠᆺ
		appendAbbreviation(abbreviations, 0x1131, 0x1160, 0x11bc, "생명"); // ᄱᅠᆼ
		appendAbbreviation(abbreviations, 0x1131, 0x1161, 0, "사무"); // ᄱᅡ
		appendAbbreviation(abbreviations, 0x1131, 0x1161, 0x11af, "사물"); // ᄱᅡᆯ
		appendAbbreviation(abbreviations, 0x1131, 0x1162, 0x11bc, "생물"); // ᄱᅢᆼ
		appendAbbreviation(abbreviations, 0x1131, 0x1165, 0x11af, "설명"); // ᄱᅥᆯ
		appendAbbreviation(abbreviations, 0x1131, 0x1167, 0x11ab, "면서 "); // ᄱᅧᆫ
		appendAbbreviation(abbreviations, 0x1131, 0x1169, 0, "소문"); // ᄱᅩ
		appendAbbreviation(abbreviations, 0x1131, 0x1169, 0x11a8, "물속"); // ᄱᅩᆨ
		appendAbbreviation(abbreviations, 0x1131, 0x1169, 0x11b8, "몹시 "); // ᄱᅩᆸ
		appendAbbreviation(abbreviations, 0x1131, 0x116c, 0, "미소"); // ᄱᅬ
		appendAbbreviation(abbreviations, 0x1131, 0x116e, 0, "무시"); // ᄱᅮ
		appendAbbreviation(abbreviations, 0x1131, 0x116e, 0x11af, "미술"); // ᄱᅮᆯ
		appendAbbreviation(abbreviations, 0x1131, 0x1175, 0, "시민"); // ᄱᅵ
		appendAbbreviation(abbreviations, 0x1131, 0x1175, 0x11a8, "식물"); // ᄱᅵᆨ
		appendAbbreviation(abbreviations, 0x1131, 0x1175, 0x11ab, "신문"); // ᄱᅵᆫ
		appendAbbreviation(abbreviations, 0x1131, 0x1175, 0x11ba, "머릿속"); // ᄱᅵᆺ
		appendAbbreviation(abbreviations, 0x1131, 0x1189, 0, "무사"); // ᄱᆉ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11a8, "학생"); // ᄲᅠᆨ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11a9, "형식"); // ᄲᅠᆩ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11ab, "현상"); // ᄲᅠᆫ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11ac, "할 수는 "); // ᄲᅠᆬ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11af, "훨씬 "); // ᄲᅠᆯ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11b0, "확실"); // ᄲᅠᆰ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11b1, "실험"); // ᄲᅠᆱ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11b7, "시험"); // ᄲᅠᆷ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11ba, "하셨"); // ᄲᅠᆺ
		appendAbbreviation(abbreviations, 0x1132, 0x1160, 0x11bc, "생활"); // ᄲᅠᆼ
		appendAbbreviation(abbreviations, 0x1132, 0x1161, 0, "사회"); // ᄲᅡ
		appendAbbreviation(abbreviations, 0x1132, 0x1161, 0x11a8, "사회적"); // ᄲᅡᆨ
		appendAbbreviation(abbreviations, 0x1132, 0x1161, 0x11ab, "한숨"); // ᄲᅡᆫ
		appendAbbreviation(abbreviations, 0x1132, 0x1161, 0x11af, "할 수"); // ᄲᅡᆯ
		appendAbbreviation(abbreviations, 0x1132, 0x1161, 0x11bc, "상당히 "); // ᄲᅡᆼ
		appendAbbreviation(abbreviations, 0x1132, 0x1162, 0, "상태"); // ᄲᅢ
		appendAbbreviation(abbreviations, 0x1132, 0x1162, 0x11a8, "핵심"); // ᄲᅢᆨ
		appendAbbreviation(abbreviations, 0x1132, 0x1162, 0x11bc, "행사"); // ᄲᅢᆼ
		appendAbbreviation(abbreviations, 0x1132, 0x1165, 0, "서서히"); // ᄲᅥ
		appendAbbreviation(abbreviations, 0x1132, 0x1165, 0x11a8, "해석"); // ᄲᅥᆨ
		appendAbbreviation(abbreviations, 0x1132, 0x1165, 0x11bc, "시청"); // ᄲᅥᆼ
		appendAbbreviation(abbreviations, 0x1132, 0x1166, 0x11ab, "센티"); // ᄲᅦᆫ
		appendAbbreviation(abbreviations, 0x1132, 0x1167, 0x11ab, "현실"); // ᄲᅧᆫ
		appendAbbreviation(abbreviations, 0x1132, 0x1167, 0x11bc, "형사"); // ᄲᅧᆼ
		appendAbbreviation(abbreviations, 0x1132, 0x1168, 0, "수혜"); // ᄲᅨ
		appendAbbreviation(abbreviations, 0x1132, 0x1169, 0, "청소"); // ᄲᅩ
		appendAbbreviation(abbreviations, 0x1132, 0x1169, 0x11a8, "혹시 "); // ᄲᅩᆨ
		appendAbbreviation(abbreviations, 0x1132, 0x116a, 0, "신화"); // ᄲᅪ
		appendAbbreviation(abbreviations, 0x1132, 0x116a, 0x11bc, "상황"); // ᄲᅪᆼ
		appendAbbreviation(abbreviations, 0x1132, 0x116c, 0, "회사"); // ᄲᅬ
		appendAbbreviation(abbreviations, 0x1132, 0x116c, 0x11ab, "신호"); // ᄲᅬᆫ
		appendAbbreviation(abbreviations, 0x1132, 0x116e, 0, "수행"); // ᄲᅮ
		appendAbbreviation(abbreviations, 0x1132, 0x1175, 0, "단순히 "); // ᄲᅵ
		appendAbbreviation(abbreviations, 0x1132, 0x1175, 0x11ab, "하신 "); // ᄲᅵᆫ
		appendAbbreviation(abbreviations, 0x1132, 0x1175, 0x11af, "실천"); // ᄲᅵᆯ
		appendAbbreviation(abbreviations, 0x1132, 0x1198, 0, "자세히 "); // ᄲᆘ
		appendAbbreviation(abbreviations, 0x1132, 0xd7c4, 0x11ab, "현실적"); // ᄲퟄᆫ
		appendAbbreviation(abbreviations, 0x1132, 0xd7c4, 0x11bc, "형성"); // ᄲퟄᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11a8, "시작"); // ᄶᅠᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11a9, "성적"); // ᄶᅠᆩ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11ab, "자신"); // ᄶᅠᆫ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11ac, "진실"); // ᄶᅠᆬ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11ad, "정신"); // ᄶᅠᆭ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11af, "솔직"); // ᄶᅠᆯ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11b0, "살짝 "); // ᄶᅠᆰ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11b4, "심장"); // ᄶᅠᆴ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11b7, "중심"); // ᄶᅠᆷ
		appendAbbreviation(abbreviations, 0x1136, 0x1160, 0x11bc, "시장"); // ᄶᅠᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1161, 0, "자식"); // ᄶᅡ
		appendAbbreviation(abbreviations, 0x1136, 0x1161, 0x11ad, "선장"); // ᄶᅡᆭ
		appendAbbreviation(abbreviations, 0x1136, 0x1161, 0x11b7, "잠시"); // ᄶᅡᆷ
		appendAbbreviation(abbreviations, 0x1136, 0x1161, 0x11bc, "사장"); // ᄶᅡᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1162, 0, "재산"); // ᄶᅢ
		appendAbbreviation(abbreviations, 0x1136, 0x1162, 0x11a8, "책상"); // ᄶᅢᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1162, 0x11af, "화장실"); // ᄶᅢᆯ
		appendAbbreviation(abbreviations, 0x1136, 0x1162, 0x11b7, "참새"); // ᄶᅢᆷ
		appendAbbreviation(abbreviations, 0x1136, 0x1162, 0x11bc, "지상"); // ᄶᅢᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1165, 0, "아저씨"); // ᄶᅥ
		appendAbbreviation(abbreviations, 0x1136, 0x1165, 0x11a8, "참석"); // ᄶᅥᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1165, 0x11ab, "조선"); // ᄶᅥᆫ
		appendAbbreviation(abbreviations, 0x1136, 0x1165, 0x11af, "질서"); // ᄶᅥᆯ
		appendAbbreviation(abbreviations, 0x1136, 0x1165, 0x11b7, "시점"); // ᄶᅥᆷ
		appendAbbreviation(abbreviations, 0x1136, 0x1165, 0x11bc, "성장"); // ᄶᅥᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1166, 0, "자세"); // ᄶᅦ
		appendAbbreviation(abbreviations, 0x1136, 0x1169, 0, "조사"); // ᄶᅩ
		appendAbbreviation(abbreviations, 0x1136, 0x1169, 0x11a8, "지속"); // ᄶᅩᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1169, 0x11bc, "장소"); // ᄶᅩᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x116a, 0x11bc, "조상"); // ᄶᅪᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x116b, 0, "소재"); // ᄶᅫ
		appendAbbreviation(abbreviations, 0x1136, 0x116c, 0x11ab, "최소한"); // ᄶᅬᆫ
		appendAbbreviation(abbreviations, 0x1136, 0x116c, 0x11b7, "조심"); // ᄶᅬᆷ
		appendAbbreviation(abbreviations, 0x1136, 0x116d, 0x11ab, "사전"); // ᄶᅭᆫ
		appendAbbreviation(abbreviations, 0x1136, 0x116d, 0x11bc, "사정"); // ᄶᅭᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x116e, 0, "수준"); // ᄶᅮ
		appendAbbreviation(abbreviations, 0x1136, 0x116e, 0x11a8, "주식"); // ᄶᅮᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x116e, 0x11af, "수출"); // ᄶᅮᆯ
		appendAbbreviation(abbreviations, 0x1136, 0x116e, 0x11bc, "소중"); // ᄶᅮᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x116f, 0, "선진국"); // ᄶᅯ
		appendAbbreviation(abbreviations, 0x1136, 0x116f, 0x11a8, "서쪽"); // ᄶᅯᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1170, 0, "중세"); // ᄶᅰ
		appendAbbreviation(abbreviations, 0x1136, 0x1170, 0x11a8, "숙제"); // ᄶᅰᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1171, 0, "제시"); // ᄶᅱ
		appendAbbreviation(abbreviations, 0x1136, 0x1171, 0x11bc, "성취"); // ᄶᅱᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1172, 0, "소주"); // ᄶᅲ
		appendAbbreviation(abbreviations, 0x1136, 0x1174, 0, "시리즈"); // ᄶᅴ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0, "지시"); // ᄶᅵ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0x11a8, "지식"); // ᄶᅵᆨ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0x11ab, "사진"); // ᄶᅵᆫ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0x11ad, "신청"); // ᄶᅵᆭ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0x11af, "실제"); // ᄶᅵᆯ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0x11b7, "점심"); // ᄶᅵᆷ
		appendAbbreviation(abbreviations, 0x1136, 0x1175, 0x11bc, "실정"); // ᄶᅵᆼ
		appendAbbreviation(abbreviations, 0x1136, 0x1189, 0, "숫자"); // ᄶᆉ
		appendAbbreviation(abbreviations, 0x1143, 0x1160, 0x11a8, "문학"); // ᅃᅠᆨ
		appendAbbreviation(abbreviations, 0x1143, 0x1160, 0x11b7, "만큼 "); // ᅃᅠᆷ
		appendAbbreviation(abbreviations, 0x1143, 0x1160, 0x11bc, "희망"); // ᅃᅠᆼ
		appendAbbreviation(abbreviations, 0x1143, 0x1161, 0x11ab, "가만히 "); // ᅃᅡᆫ
		appendAbbreviation(abbreviations, 0x1143, 0x1162, 0, "치마"); // ᅃᅢ
		appendAbbreviation(abbreviations, 0x1143, 0x1166, 0, "매체"); // ᅃᅦ
		appendAbbreviation(abbreviations, 0x1143, 0x1167, 0x11ab, "화면"); // ᅃᅧᆫ
		appendAbbreviation(abbreviations, 0x1143, 0x116a, 0, "만화"); // ᅃᅪ
		appendAbbreviation(abbreviations, 0x1143, 0x116e, 0x11ab, "흥미로운 "); // ᅃᅮᆫ
		appendAbbreviation(abbreviations, 0x1143, 0x1170, 0, "물체"); // ᅃᅰ
		appendAbbreviation(abbreviations, 0x1143, 0x1173, 0x11a8, "측면"); // ᅃᅳᆨ
		appendAbbreviation(abbreviations, 0x1143, 0x1173, 0x11b7, "그만큼"); // ᅃᅳᆷ
		appendAbbreviation(abbreviations, 0x1143, 0x1173, 0x11bc, "흥미"); // ᅃᅳᆼ
		appendAbbreviation(abbreviations, 0x1143, 0x1175, 0, "미터"); // ᅃᅵ
		appendAbbreviation(abbreviations, 0x1143, 0x1175, 0x11b7, "침묵"); // ᅃᅵᆷ
		appendAbbreviation(abbreviations, 0x1143, 0x1189, 0x11ab, "문화재"); // ᅃᆉᆫ
		appendAbbreviation(abbreviations, 0x1143, 0x1198, 0, "카메라"); // ᅃᆘ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11a8, "역할"); // ᅊᅠᆨ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11ab, "차원"); // ᅊᅠᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11af, "올해"); // ᅊᅠᆯ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11b7, "책임"); // ᅊᅠᆷ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11b8, "연합"); // ᅊᅠᆸ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11ba, "하였"); // ᅊᅠᆺ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11bc, "영화"); // ᅊᅠᆼ
		appendAbbreviation(abbreviations, 0x114a, 0x1160, 0x11fa, "확인"); // ᅊᅠᇺ
		appendAbbreviation(abbreviations, 0x114a, 0x1161, 0, "아파트"); // ᅊᅡ
		appendAbbreviation(abbreviations, 0x114a, 0x1161, 0x11a8, "유학"); // ᅊᅡᆨ
		appendAbbreviation(abbreviations, 0x114a, 0x1161, 0x11ab, "또한 "); // ᅊᅡᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1161, 0x11b7, "참여"); // ᅊᅡᆷ
		appendAbbreviation(abbreviations, 0x114a, 0x1162, 0, "차이"); // ᅊᅢ
		appendAbbreviation(abbreviations, 0x114a, 0x1162, 0x11ab, "한때"); // ᅊᅢᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1162, 0x11bc, "행위"); // ᅊᅢᆼ
		appendAbbreviation(abbreviations, 0x114a, 0x1165, 0x11b7, "위험"); // ᅊᅥᆷ
		appendAbbreviation(abbreviations, 0x114a, 0x1165, 0x11bc, "엄청 "); // ᅊᅥᆼ
		appendAbbreviation(abbreviations, 0x114a, 0x1166, 0, "업체"); // ᅊᅦ
		appendAbbreviation(abbreviations, 0x114a, 0x1167, 0, "하여 "); // ᅊᅧ
		appendAbbreviation(abbreviations, 0x114a, 0x1167, 0x11ab, "편안"); // ᅊᅧᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1167, 0x11b8, "위협"); // ᅊᅧᆸ
		appendAbbreviation(abbreviations, 0x114a, 0x1167, 0x11bc, "영향"); // ᅊᅧᆼ
		appendAbbreviation(abbreviations, 0x114a, 0x1169, 0, "오히려 "); // ᅊᅩ
		appendAbbreviation(abbreviations, 0x114a, 0x1169, 0x11a8, "혹은 "); // ᅊᅩᆨ
		appendAbbreviation(abbreviations, 0x114a, 0x1169, 0x11bc, "통일"); // ᅊᅩᆼ
		appendAbbreviation(abbreviations, 0x114a, 0x116a, 0x11ab, "완전히 "); // ᅊᅪᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x116b, 0, "해외"); // ᅊᅫ
		appendAbbreviation(abbreviations, 0x114a, 0x116c, 0, "회의"); // ᅊᅬ
		appendAbbreviation(abbreviations, 0x114a, 0x116c, 0x11ab, "회원"); // ᅊᅬᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x116d, 0x11bc, "조용히 "); // ᅊᅭᆼ
		appendAbbreviation(abbreviations, 0x114a, 0x116e, 0, "이후"); // ᅊᅮ
		appendAbbreviation(abbreviations, 0x114a, 0x116f, 0x11ab, "원한"); // ᅊᅯᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1170, 0, "체육"); // ᅊᅰ
		appendAbbreviation(abbreviations, 0x114a, 0x1170, 0x11a8, "육체"); // ᅊᅰᆨ
		appendAbbreviation(abbreviations, 0x114a, 0x1171, 0, "위치"); // ᅊᅱ
		appendAbbreviation(abbreviations, 0x114a, 0x1171, 0x11ab, "위원회"); // ᅊᅱᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1172, 0, "오후"); // ᅊᅲ
		appendAbbreviation(abbreviations, 0x114a, 0x1173, 0x11ab, "은행"); // ᅊᅳᆫ
		appendAbbreviation(abbreviations, 0x114a, 0x1173, 0x11af, "이틀"); // ᅊᅳᆯ
		appendAbbreviation(abbreviations, 0x114a, 0x1175, 0, "이해"); // ᅊᅵ
		appendAbbreviation(abbreviations, 0x114a, 0x1175, 0x11af, "일치"); // ᅊᅵᆯ
		appendAbbreviation(abbreviations, 0x114a, 0x1175, 0x11b8, "입학"); // ᅊᅵᆸ
		appendAbbreviation(abbreviations, 0x114a, 0x119e, 0, "의학"); // ᅊᆞ
		appendAbbreviation(abbreviations, 0x114a, 0xd7bf, 0, "헤어"); // ᅊힿ
		appendAbbreviation(abbreviations, 0x114a, 0xd7c4, 0, "여전히 "); // ᅊퟄ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11ab, "오는 "); // ᅌᅠᆫ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11ad, "안녕"); // ᅌᅠᆭ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11af, "얼마나 "); // ᅌᅠᆯ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11b3, "옛날"); // ᅌᅠᆳ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11b7, "넘어"); // ᅌᅠᆷ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11b9, "없는 "); // ᅌᅠᆹ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11ba, "있는 "); // ᅌᅠᆺ
		appendAbbreviation(abbreviations, 0x114c, 0x1160, 0x11bc, "내용"); // ᅌᅠᆼ
		appendAbbreviation(abbreviations, 0x114c, 0x1161, 0, "아니"); // ᅌᅡ
		appendAbbreviation(abbreviations, 0x114c, 0x1161, 0x11af, "이날"); // ᅌᅡᆯ
		appendAbbreviation(abbreviations, 0x114c, 0x1162, 0, "아내"); // ᅌᅢ
		appendAbbreviation(abbreviations, 0x114c, 0x1165, 0, "어머니"); // ᅌᅥ
		appendAbbreviation(abbreviations, 0x114c, 0x1165, 0x11ab, "언니"); // ᅌᅥᆫ
		appendAbbreviation(abbreviations, 0x114c, 0x1165, 0x11b9, "없애는 "); // ᅌᅥᆹ
		appendAbbreviation(abbreviations, 0x114c, 0x1167, 0, "이념"); // ᅌᅧ
		appendAbbreviation(abbreviations, 0x114c, 0x1169, 0, "오늘"); // ᅌᅩ
		appendAbbreviation(abbreviations, 0x114c, 0x1169, 0x11ab, "논의"); // ᅌᅩᆫ
		appendAbbreviation(abbreviations, 0x114c, 0x1169, 0x11bc, "농업"); // ᅌᅩᆼ
		appendAbbreviation(abbreviations, 0x114c, 0x116a, 0, "오늘날"); // ᅌᅪ
		appendAbbreviation(abbreviations, 0x114c, 0x116e, 0, "우는 "); // ᅌᅮ
		appendAbbreviation(abbreviations, 0x114c, 0x1173, 0, "이는 "); // ᅌᅳ
		appendAbbreviation(abbreviations, 0x114c, 0x1173, 0x11af, "늘어"); // ᅌᅳᆯ
		appendAbbreviation(abbreviations, 0x114c, 0x1174, 0, "으니"); // ᅌᅴ
		appendAbbreviation(abbreviations, 0x114c, 0x1175, 0, "니까"); // ᅌᅵ
		appendAbbreviation(abbreviations, 0x114c, 0x1175, 0x11ab, "노인"); // ᅌᅵᆫ
		appendAbbreviation(abbreviations, 0x114c, 0x1175, 0x11af, "내일"); // ᅌᅵᆯ
		appendAbbreviation(abbreviations, 0x114c, 0x1175, 0x11b7, "느낌"); // ᅌᅵᆷ
		appendAbbreviation(abbreviations, 0x114c, 0x1189, 0, "눈앞"); // ᅌᆉ
		appendAbbreviation(abbreviations, 0x114c, 0x119e, 0, "어느새 "); // ᅌᆞ
		appendAbbreviation(abbreviations, 0x114c, 0x11a2, 0, "어느 "); // ᅌᆢ
		appendAbbreviation(abbreviations, 0x114c, 0xd7bc, 0, "높은 "); // ᅌힼ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11a8, "중국"); // ᅒᅠᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11a9, "충격"); // ᅒᅠᆩ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11ab, "친구"); // ᅒᅠᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11ac, "관찰"); // ᅒᅠᆬ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11ad, "괜찮"); // ᅒᅠᆭ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11af, "경찰"); // ᅒᅠᆯ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11b2, "껍질"); // ᅒᅠᆲ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11b5, "중소기업"); // ᅒᅠᆵ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11b7, "지금"); // ᅒᅠᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11b8, "갑자기 "); // ᅒᅠᆸ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11ba, "거짓"); // ᅒᅠᆺ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11bc, "가장 "); // ᅒᅠᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11c0, "잠깐"); // ᅒᅠᇀ
		appendAbbreviation(abbreviations, 0x1152, 0x1160, 0x11fa, "건축"); // ᅒᅠᇺ
		appendAbbreviation(abbreviations, 0x1152, 0x1161, 0, "자기"); // ᅒᅡ
		appendAbbreviation(abbreviations, 0x1152, 0x1161, 0x11a8, "작가"); // ᅒᅡᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1161, 0x11ab, "관계자"); // ᅒᅡᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1161, 0x11ad, "장관"); // ᅒᅡᆭ
		appendAbbreviation(abbreviations, 0x1152, 0x1161, 0x11b7, "참가"); // ᅒᅡᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x1161, 0x11bc, "공장"); // ᅒᅡᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1162, 0, "가지"); // ᅒᅢ
		appendAbbreviation(abbreviations, 0x1152, 0x1162, 0x11a8, "개인적"); // ᅒᅢᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1162, 0x11ab, "전개"); // ᅒᅢᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1162, 0x11bc, "경쟁"); // ᅒᅢᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1165, 0, "저기"); // ᅒᅥ
		appendAbbreviation(abbreviations, 0x1152, 0x1165, 0x11a8, "걱정"); // ᅒᅥᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1165, 0x11ab, "조건"); // ᅒᅥᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1165, 0x11b7, "검찰"); // ᅒᅥᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x1165, 0x11b8, "접근"); // ᅒᅥᆸ
		appendAbbreviation(abbreviations, 0x1152, 0x1165, 0x11bc, "가정"); // ᅒᅥᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1166, 0, "구체적"); // ᅒᅦ
		appendAbbreviation(abbreviations, 0x1152, 0x1166, 0x11bc, "제공"); // ᅒᅦᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1167, 0, "가져"); // ᅒᅧ
		appendAbbreviation(abbreviations, 0x1152, 0x1167, 0x11a8, "자격"); // ᅒᅧᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1167, 0x11af, "결정"); // ᅒᅧᆯ
		appendAbbreviation(abbreviations, 0x1152, 0x1167, 0x11bc, "경제"); // ᅒᅧᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1168, 0, "체계"); // ᅒᅨ
		appendAbbreviation(abbreviations, 0x1152, 0x1169, 0, "최고"); // ᅒᅩ
		appendAbbreviation(abbreviations, 0x1152, 0x1169, 0x11a8, "가족"); // ᅒᅩᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1169, 0x11ab, "기존"); // ᅒᅩᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1169, 0x11ad, "주인공"); // ᅒᅩᆭ
		appendAbbreviation(abbreviations, 0x1152, 0x1169, 0x11bc, "각종"); // ᅒᅩᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x116a, 0, "과정"); // ᅒᅪ
		appendAbbreviation(abbreviations, 0x1152, 0x116a, 0x11a8, "과학자"); // ᅒᅪᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x116a, 0x11ab, "관점"); // ᅒᅪᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x116a, 0x11bc, "강조"); // ᅒᅪᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x116c, 0, "초기"); // ᅒᅬ
		appendAbbreviation(abbreviations, 0x1152, 0x116c, 0x11a8, "귀족"); // ᅒᅬᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x116c, 0x11ab, "최근 "); // ᅒᅬᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x116c, 0x11bc, "굉장"); // ᅒᅬᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x116d, 0, "종교"); // ᅒᅭ
		appendAbbreviation(abbreviations, 0x1152, 0x116d, 0x11ab, "자전거"); // ᅒᅭᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x116d, 0x11b7, "감정"); // ᅒᅭᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x116d, 0x11bc, "교장"); // ᅒᅭᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x116e, 0, "구조"); // ᅒᅮ
		appendAbbreviation(abbreviations, 0x1152, 0x116e, 0x11a8, "축구"); // ᅒᅮᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x116e, 0x11ab, "기준"); // ᅒᅮᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x116e, 0x11af, "줄기"); // ᅒᅮᆯ
		appendAbbreviation(abbreviations, 0x1152, 0x116e, 0x11bc, "중간"); // ᅒᅮᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x116f, 0, "전국"); // ᅒᅯ
		appendAbbreviation(abbreviations, 0x1152, 0x116f, 0x11a8, "과학적"); // ᅒᅯᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x116f, 0x11ab, "정권"); // ᅒᅯᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1170, 0x11a8, "국제"); // ᅒᅰᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1171, 0, "치게 "); // ᅒᅱ
		appendAbbreviation(abbreviations, 0x1152, 0x1172, 0, "규정"); // ᅒᅲ
		appendAbbreviation(abbreviations, 0x1152, 0x1173, 0, "그저 "); // ᅒᅳ
		appendAbbreviation(abbreviations, 0x1152, 0x1173, 0x11a8, "자극"); // ᅒᅳᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x1173, 0x11ab, "근처"); // ᅒᅳᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1173, 0x11af, "즐거운 "); // ᅒᅳᆯ
		appendAbbreviation(abbreviations, 0x1152, 0x1173, 0x11b7, "자금"); // ᅒᅳᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x1174, 0, "그렇지"); // ᅒᅴ
		appendAbbreviation(abbreviations, 0x1152, 0x1174, 0x11b7, "금지"); // ᅒᅴᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0, "까지 "); // ᅒᅵ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0x11ab, "전기"); // ᅒᅵᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0x11ad, "긴장"); // ᅒᅵᆭ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0x11af, "거칠"); // ᅒᅵᆯ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0x11b4, "김정"); // ᅒᅵᆴ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0x11b7, "김치"); // ᅒᅵᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x1175, 0x11bc, "지경"); // ᅒᅵᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1189, 0, "자꾸 "); // ᅒᆉ
		appendAbbreviation(abbreviations, 0x1152, 0x1189, 0x11ab, "장군"); // ᅒᆉᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x1189, 0x11bc, "광주"); // ᅒᆉᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x1195, 0, "그중"); // ᅒᆕ
		appendAbbreviation(abbreviations, 0x1152, 0x1198, 0, "과제"); // ᅒᆘ
		appendAbbreviation(abbreviations, 0x1152, 0x1198, 0x11bc, "강제"); // ᅒᆘᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x119e, 0, "글자"); // ᅒᆞ
		appendAbbreviation(abbreviations, 0x1152, 0x119e, 0x11a8, "극장"); // ᅒᆞᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x119e, 0x11b7, "그림자"); // ᅒᆞᆷ
		appendAbbreviation(abbreviations, 0x1152, 0x119e, 0x11bc, "증가"); // ᅒᆞᆼ
		appendAbbreviation(abbreviations, 0x1152, 0x11a2, 0, "적극"); // ᅒᆢ
		appendAbbreviation(abbreviations, 0x1152, 0x11a2, 0x11a8, "적극적"); // ᅒᆢᆨ
		appendAbbreviation(abbreviations, 0x1152, 0x11a2, 0x11ab, "증권"); // ᅒᆢᆫ
		appendAbbreviation(abbreviations, 0x1152, 0x11a2, 0x11bc, "증거"); // ᅒᆢᆼ
		appendAbbreviation(abbreviations, 0x1152, 0xd7bb, 0, "계층"); // ᅒힻ
		appendAbbreviation(abbreviations, 0x1152, 0xd7bc, 0, "조그"); // ᅒힼ
		appendAbbreviation(abbreviations, 0x1152, 0xd7bf, 0, "제거"); // ᅒힿ
		appendAbbreviation(abbreviations, 0x1152, 0xd7bf, 0x11af, "계절"); // ᅒힿᆯ
		appendAbbreviation(abbreviations, 0x1152, 0xd7c4, 0, "여기저기"); // ᅒퟄ
		appendAbbreviation(abbreviations, 0x1152, 0xd7c4, 0x11bc, "경제적"); // ᅒퟄᆼ
		appendAbbreviation(abbreviations, 0x1157, 0x1160, 0x11a8, "작년"); // ᅗᅠᆨ
		appendAbbreviation(abbreviations, 0x1157, 0x1160, 0x11ab, "는지 "); // ᅗᅠᆫ
		appendAbbreviation(abbreviations, 0x1157, 0x1160, 0x11b7, "주님"); // ᅗᅠᆷ
		appendAbbreviation(abbreviations, 0x1157, 0x1160, 0x11bc, "나중"); // ᅗᅠᆼ
		appendAbbreviation(abbreviations, 0x1157, 0x1161, 0x11ab, "지난 "); // ᅗᅡᆫ
		appendAbbreviation(abbreviations, 0x1157, 0x1162, 0, "내지 "); // ᅗᅢ
		appendAbbreviation(abbreviations, 0x1157, 0x1162, 0x11ab, "지난해"); // ᅗᅢᆫ
		appendAbbreviation(abbreviations, 0x1157, 0x1162, 0x11af, "지난달"); // ᅗᅢᆯ
		appendAbbreviation(abbreviations, 0x1157, 0x1165, 0, "저녁"); // ᅗᅥ
		appendAbbreviation(abbreviations, 0x1157, 0x1166, 0, "에너지"); // ᅗᅦ
		appendAbbreviation(abbreviations, 0x1157, 0x1167, 0, "자녀"); // ᅗᅧ
		appendAbbreviation(abbreviations, 0x1157, 0x1169, 0x11a8, "남쪽"); // ᅗᅩᆨ
		appendAbbreviation(abbreviations, 0x1157, 0x1173, 0x11ab, "지는 "); // ᅗᅳᆫ
		appendAbbreviation(abbreviations, 0x1157, 0x1175, 0, "지나"); // ᅗᅵ
		appendAbbreviation(abbreviations, 0x1157, 0x1198, 0, "자네"); // ᅗᆘ
		appendAbbreviation(abbreviations, 0x1159, 0x1160, 0x11a8, "학년"); // ᅙᅠᆨ
		appendAbbreviation(abbreviations, 0x1159, 0x1160, 0x11ab, "키는 "); // ᅙᅠᆫ
		appendAbbreviation(abbreviations, 0x1159, 0x1160, 0x11af, "하늘"); // ᅙᅠᆯ
		appendAbbreviation(abbreviations, 0x1159, 0x1160, 0x11b7, "하나님"); // ᅙᅠᆷ
		appendAbbreviation(abbreviations, 0x1159, 0x1161, 0, "하나"); // ᅙᅡ
		appendAbbreviation(abbreviations, 0x1159, 0x1161, 0x11ab, "남한"); // ᅙᅡᆫ
		appendAbbreviation(abbreviations, 0x1159, 0x1165, 0x11bc, "청소년"); // ᅙᅥᆼ
		appendAbbreviation(abbreviations, 0x1159, 0x1166, 0, "하는데"); // ᅙᅦ
		appendAbbreviation(abbreviations, 0x1159, 0x1167, 0x11bc, "형님"); // ᅙᅧᆼ
		appendAbbreviation(abbreviations, 0x1159, 0x1169, 0x11ab, "농촌"); // ᅙᅩᆫ
		appendAbbreviation(abbreviations, 0x1159, 0x116b, 0, "왜냐하면 "); // ᅙᅫ
		appendAbbreviation(abbreviations, 0x1159, 0x1173, 0, "하는 "); // ᅙᅳ
		appendAbbreviation(abbreviations, 0x1159, 0x1173, 0x11ab, "치는 "); // ᅙᅳᆫ
		appendAbbreviation(abbreviations, 0x1159, 0x119e, 0, "찾는 "); // ᅙᆞ
		appendAbbreviation(abbreviations, 0x1159, 0x11a2, 0, "너희"); // ᅙᆢ
		appendAbbreviation(abbreviations, 0x1159, 0xd7c4, 0, "청년"); // ᅙퟄ
		appendAbbreviation(abbreviations, 0x115e, 0x1160, 0x11a8, "도록 "); // ᅞᅠᆨ
		appendAbbreviation(abbreviations, 0x115e, 0x1160, 0x11ad, "오랫동안"); // ᅞᅠᆭ
		appendAbbreviation(abbreviations, 0x115e, 0x1160, 0x11af, "달리"); // ᅞᅠᆯ
		appendAbbreviation(abbreviations, 0x115e, 0x1160, 0x11bc, "대통령"); // ᅞᅠᆼ
		appendAbbreviation(abbreviations, 0x115e, 0x1161, 0, "따라"); // ᅞᅡ
		appendAbbreviation(abbreviations, 0x115e, 0x1161, 0x11af, "달라"); // ᅞᅡᆯ
		appendAbbreviation(abbreviations, 0x115e, 0x1162, 0, "대로 "); // ᅞᅢ
		appendAbbreviation(abbreviations, 0x115e, 0x1165, 0, "따라서 "); // ᅞᅥ
		appendAbbreviation(abbreviations, 0x115e, 0x1166, 0, "텔레비전"); // ᅞᅦ
		appendAbbreviation(abbreviations, 0x115e, 0x1169, 0, "따로 "); // ᅞᅩ
		appendAbbreviation(abbreviations, 0x115e, 0x1169, 0x11ab, "토론"); // ᅞᅩᆫ
		appendAbbreviation(abbreviations, 0x115e, 0x1169, 0x11bc, "동료"); // ᅞᅩᆼ
		appendAbbreviation(abbreviations, 0x115e, 0x116a, 0, "라도 "); // ᅞᅪ
		appendAbbreviation(abbreviations, 0x115e, 0x116b, 0, "때로 "); // ᅞᅫ
		appendAbbreviation(abbreviations, 0x115e, 0x116d, 0, "더라. "); // ᅞᅭ
		appendAbbreviation(abbreviations, 0x115e, 0x1173, 0, "그대로 "); // ᅞᅳ
		appendAbbreviation(abbreviations, 0x115e, 0x1173, 0x11bc, "등록"); // ᅞᅳᆼ
		appendAbbreviation(abbreviations, 0x115e, 0x1175, 0, "다리"); // ᅞᅵ
		appendAbbreviation(abbreviations, 0x115e, 0x1175, 0x11b8, "독립"); // ᅞᅵᆸ
		appendAbbreviation(abbreviations, 0x115e, 0x1189, 0, "아무래도 "); // ᅞᆉ
		appendAbbreviation(abbreviations, 0x115e, 0x119e, 0, "드라마"); // ᅞᆞ
		appendAbbreviation(abbreviations, 0x115e, 0x11a2, 0, "드러"); // ᅞᆢ
		appendAbbreviation(abbreviations, 0xa962, 0x1160, 0x11a8, "속도"); // ꥢᅠᆨ
		appendAbbreviation(abbreviations, 0xa962, 0x1160, 0x11a9, "특성"); // ꥢᅠᆩ
		appendAbbreviation(abbreviations, 0xa962, 0x1160, 0x11ab, "당신"); // ꥢᅠᆫ
		appendAbbreviation(abbreviations, 0xa962, 0x1160, 0x11b9, " 수도 없"); // ꥢᅠᆹ
		appendAbbreviation(abbreviations, 0xa962, 0x1160, 0x11ba, " 수도 있"); // ꥢᅠᆺ
		appendAbbreviation(abbreviations, 0xa962, 0x1160, 0x11bc, "당시"); // ꥢᅠᆼ
		appendAbbreviation(abbreviations, 0xa962, 0x1161, 0, "시다. "); // ꥢᅡ
		appendAbbreviation(abbreviations, 0xa962, 0x1161, 0x11ab, "수단"); // ꥢᅡᆫ
		appendAbbreviation(abbreviations, 0xa962, 0x1161, 0x11bc, "대상"); // ꥢᅡᆼ
		appendAbbreviation(abbreviations, 0xa962, 0x1162, 0, "다시 "); // ꥢᅢ
		appendAbbreviation(abbreviations, 0xa962, 0x1162, 0x11a8, "택시"); // ꥢᅢᆨ
		appendAbbreviation(abbreviations, 0xa962, 0x1162, 0x11a9, "상대적"); // ꥢᅢᆩ
		appendAbbreviation(abbreviations, 0xa962, 0x1162, 0x11bc, "동생"); // ꥢᅢᆼ
		appendAbbreviation(abbreviations, 0xa962, 0x1165, 0x11ab, "선택"); // ꥢᅥᆫ
		appendAbbreviation(abbreviations, 0xa962, 0x1165, 0x11af, "설탕"); // ꥢᅥᆯ
		appendAbbreviation(abbreviations, 0xa962, 0x1166, 0, "세대"); // ꥢᅦ
		appendAbbreviation(abbreviations, 0xa962, 0x1166, 0x11b7, "시스템"); // ꥢᅦᆷ
		appendAbbreviation(abbreviations, 0xa962, 0x1169, 0, "도시"); // ꥢᅩ
		appendAbbreviation(abbreviations, 0xa962, 0x1169, 0x11bc, "동시"); // ꥢᅩᆼ
		appendAbbreviation(abbreviations, 0xa962, 0x116a, 0, "다소 "); // ꥢᅪ
		appendAbbreviation(abbreviations, 0xa962, 0x116b, 0, "또다시 "); // ꥢᅫ
		appendAbbreviation(abbreviations, 0xa962, 0x116c, 0, "시도"); // ꥢᅬ
		appendAbbreviation(abbreviations, 0xa962, 0x116e, 0, "수도"); // ꥢᅮ
		appendAbbreviation(abbreviations, 0xa962, 0x116e, 0x11ab, "단순"); // ꥢᅮᆫ
		appendAbbreviation(abbreviations, 0xa962, 0x116f, 0, "도서관"); // ꥢᅯ
		appendAbbreviation(abbreviations, 0xa962, 0x1173, 0, "스트레스"); // ꥢᅳ
		appendAbbreviation(abbreviations, 0xa962, 0x1173, 0x11a8, "소득"); // ꥢᅳᆨ
		appendAbbreviation(abbreviations, 0xa962, 0x1175, 0, "시대"); // ꥢᅵ
		appendAbbreviation(abbreviations, 0xa962, 0x1175, 0x11a8, "식당"); // ꥢᅵᆨ
		appendAbbreviation(abbreviations, 0xa962, 0x1175, 0x11ab, "대신"); // ꥢᅵᆫ
		appendAbbreviation(abbreviations, 0xa962, 0x1189, 0, "다수"); // ꥢᆉ
		appendAbbreviation(abbreviations, 0xa962, 0x1195, 0, "특수"); // ꥢᆕ
		appendAbbreviation(abbreviations, 0xa962, 0x119e, 0, "스타"); // ꥢᆞ
		appendAbbreviation(abbreviations, 0xa962, 0x119e, 0x11af, "스타일"); // ꥢᆞᆯ
		appendAbbreviation(abbreviations, 0xa963, 0x1160, 0x11a8, "적당"); // ꥣᅠᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x1160, 0x11a9, "동작"); // ꥣᅠᆩ
		appendAbbreviation(abbreviations, 0xa963, 0x1160, 0x11ab, "든지 "); // ꥣᅠᆫ
		appendAbbreviation(abbreviations, 0xa963, 0x1160, 0x11af, "절대"); // ꥣᅠᆯ
		appendAbbreviation(abbreviations, 0xa963, 0x1160, 0x11b7, "다짐"); // ꥣᅠᆷ
		appendAbbreviation(abbreviations, 0xa963, 0x1160, 0x11b8, "집단"); // ꥣᅠᆸ
		appendAbbreviation(abbreviations, 0xa963, 0x1161, 0, "자동차"); // ꥣᅡ
		appendAbbreviation(abbreviations, 0xa963, 0x1161, 0x11a8, "도착"); // ꥣᅡᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x1161, 0x11ab, "단지"); // ꥣᅡᆫ
		appendAbbreviation(abbreviations, 0xa963, 0x1161, 0x11af, "전달"); // ꥣᅡᆯ
		appendAbbreviation(abbreviations, 0xa963, 0x1161, 0x11bc, "당장"); // ꥣᅡᆼ
		appendAbbreviation(abbreviations, 0xa963, 0x1162, 0, "제대로 "); // ꥣᅢ
		appendAbbreviation(abbreviations, 0xa963, 0x1162, 0x11a8, "대책"); // ꥣᅢᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x1165, 0x11a8, "적어도 "); // ꥣᅥᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x1165, 0x11ab, "전통"); // ꥣᅥᆫ
		appendAbbreviation(abbreviations, 0xa963, 0x1165, 0x11bc, "정당"); // ꥣᅥᆼ
		appendAbbreviation(abbreviations, 0xa963, 0x1165, 0x11fa, "전통적"); // ꥣᅥᇺ
		appendAbbreviation(abbreviations, 0xa963, 0x1166, 0, "제도"); // ꥣᅦ
		appendAbbreviation(abbreviations, 0xa963, 0x1168, 0x11bc, "통제"); // ꥣᅨᆼ
		appendAbbreviation(abbreviations, 0xa963, 0x1169, 0, "토지"); // ꥣᅩ
		appendAbbreviation(abbreviations, 0xa963, 0x1169, 0x11a8, "동쪽"); // ꥣᅩᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x116a, 0, "청와대"); // ꥣᅪ
		appendAbbreviation(abbreviations, 0xa963, 0x116e, 0, "투쟁"); // ꥣᅮ
		appendAbbreviation(abbreviations, 0xa963, 0x116e, 0x11a8, "주택"); // ꥣᅮᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x116e, 0x11bc, "대중"); // ꥣᅮᆼ
		appendAbbreviation(abbreviations, 0xa963, 0x116f, 0x11ab, "전도"); // ꥣᅯᆫ
		appendAbbreviation(abbreviations, 0xa963, 0x1173, 0x11a8, "특징"); // ꥣᅳᆨ
		appendAbbreviation(abbreviations, 0xa963, 0x1173, 0x11bc, "등장"); // ꥣᅳᆼ
		appendAbbreviation(abbreviations, 0xa963, 0x1175, 0, "지도"); // ꥣᅵ
		appendAbbreviation(abbreviations, 0xa963, 0x1175, 0x11ab, "진단"); // ꥣᅵᆫ
		appendAbbreviation(abbreviations, 0xa963, 0x1175, 0x11b7, "침대"); // ꥣᅵᆷ
		appendAbbreviation(abbreviations, 0xa963, 0x1189, 0, "투자"); // ꥣᆉ
		appendAbbreviation(abbreviations, 0xa963, 0x1198, 0, "대체"); // ꥣᆘ
		appendAbbreviation(abbreviations, 0xa963, 0x119e, 0, "잔뜩"); // ꥣᆞ
		appendAbbreviation(abbreviations, 0xa963, 0x11a2, 0, "어쨌든 "); // ꥣᆢ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11a8, "권력"); // ꥤᅠᆨ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11ab, "그런 "); // ꥤᅠᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11af, "이데올로기"); // ꥤᅠᆯ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11b7, "그램"); // ꥤᅠᆷ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11b8, "그룹"); // ꥤᅠᆸ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11ba, "그랬"); // ꥤᅠᆺ
		appendAbbreviation(abbreviations, 0xa964, 0x1160, 0x11c2, "그렇"); // ꥤᅠᇂ
		appendAbbreviation(abbreviations, 0xa964, 0x1161, 0, "그러나 "); // ꥤᅡ
		appendAbbreviation(abbreviations, 0xa964, 0x1161, 0x11a8, "가락"); // ꥤᅡᆨ
		appendAbbreviation(abbreviations, 0xa964, 0x1161, 0x11ab, "커다란 "); // ꥤᅡᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x1161, 0x11bc, "강력"); // ꥤᅡᆼ
		appendAbbreviation(abbreviations, 0xa964, 0x1162, 0, "그래"); // ꥤᅢ
		appendAbbreviation(abbreviations, 0xa964, 0x1164, 0, "고려"); // ꥤᅤ
		appendAbbreviation(abbreviations, 0xa964, 0x1165, 0, "그래서 "); // ꥤᅥ
		appendAbbreviation(abbreviations, 0xa964, 0x1165, 0x11b7, "그럼 "); // ꥤᅥᆷ
		appendAbbreviation(abbreviations, 0xa964, 0x1166, 0, "그런데 "); // ꥤᅦ
		appendAbbreviation(abbreviations, 0xa964, 0x1167, 0, "려고 "); // ꥤᅧ
		appendAbbreviation(abbreviations, 0xa964, 0x1167, 0x11ab, "관련"); // ꥤᅧᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x1167, 0x11bc, "가령 "); // ꥤᅧᆼ
		appendAbbreviation(abbreviations, 0xa964, 0x1168, 0, "롭게 "); // ꥤᅨ
		appendAbbreviation(abbreviations, 0xa964, 0x1169, 0, "그리고 "); // ꥤᅩ
		appendAbbreviation(abbreviations, 0xa964, 0x1169, 0x11a8, "기록"); // ꥤᅩᆨ
		appendAbbreviation(abbreviations, 0xa964, 0x1169, 0x11ab, "결론"); // ꥤᅩᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x116a, 0, "라고"); // ꥤᅪ
		appendAbbreviation(abbreviations, 0xa964, 0x116a, 0x11ab, "관리"); // ꥤᅪᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x116c, 0, "꼬리"); // ꥤᅬ
		appendAbbreviation(abbreviations, 0xa964, 0x116e, 0, "고구려"); // ꥤᅮ
		appendAbbreviation(abbreviations, 0xa964, 0x116f, 0x11ab, "권리"); // ꥤᅯᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x1173, 0, "그래도 "); // ꥤᅳ
		appendAbbreviation(abbreviations, 0xa964, 0x1173, 0x11ab, "근로"); // ꥤᅳᆫ
		appendAbbreviation(abbreviations, 0xa964, 0x1173, 0x11b7, "기름"); // ꥤᅳᆷ
		appendAbbreviation(abbreviations, 0xa964, 0x1173, 0x11ba, "그릇"); // ꥤᅳᆺ
		appendAbbreviation(abbreviations, 0xa964, 0x1174, 0, "그리"); // ꥤᅴ
		appendAbbreviation(abbreviations, 0xa964, 0x1175, 0, "거리"); // ꥤᅵ
		appendAbbreviation(abbreviations, 0xa964, 0x1175, 0x11af, "킬로"); // ꥤᅵᆯ
		appendAbbreviation(abbreviations, 0xa964, 0x1175, 0x11b7, "그림"); // ꥤᅵᆷ
		appendAbbreviation(abbreviations, 0xa964, 0x1189, 0, "가루"); // ꥤᆉ
		appendAbbreviation(abbreviations, 0xa964, 0x1195, 0, "구름"); // ꥤᆕ
		appendAbbreviation(abbreviations, 0xa964, 0x119e, 0, "가르"); // ꥤᆞ
		appendAbbreviation(abbreviations, 0xa964, 0x11a2, 0, "그러"); // ꥤᆢ
		appendAbbreviation(abbreviations, 0xa964, 0xd7b0, 0, "그리하여 "); // ꥤힰ
		appendAbbreviation(abbreviations, 0xa964, 0xd7c4, 0, "그러려고 "); // ꥤퟄ
		appendAbbreviation(abbreviations, 0xa964, 0xd7c4, 0x11ab, "그러려면 "); // ꥤퟄᆫ
		appendAbbreviation(abbreviations, 0xa968, 0x1160, 0x11ab, "물론 "); // ꥨᅠᆫ
		appendAbbreviation(abbreviations, 0xa968, 0x1160, 0x11af, "멀리 "); // ꥨᅠᆯ
		appendAbbreviation(abbreviations, 0xa968, 0x1160, 0x11bc, "명령"); // ꥨᅠᆼ
		appendAbbreviation(abbreviations, 0xa968, 0x1161, 0, "마리"); // ꥨᅡ
		appendAbbreviation(abbreviations, 0xa968, 0x1161, 0x11ab, "마련"); // ꥨᅡᆫ
		appendAbbreviation(abbreviations, 0xa968, 0x1161, 0x11af, "야말로 "); // ꥨᅡᆯ
		appendAbbreviation(abbreviations, 0xa968, 0x1162, 0, "미래"); // ꥨᅢ
		appendAbbreviation(abbreviations, 0xa968, 0x1162, 0x11a8, "매력"); // ꥨᅢᆨ
		appendAbbreviation(abbreviations, 0xa968, 0x1165, 0, "머리"); // ꥨᅥ
		appendAbbreviation(abbreviations, 0xa968, 0x1165, 0x11ab, "아무런 "); // ꥨᅥᆫ
		appendAbbreviation(abbreviations, 0xa968, 0x1167, 0, "려면 "); // ꥨᅧ
		appendAbbreviation(abbreviations, 0xa968, 0x1167, 0x11ab, "라면"); // ꥨᅧᆫ
		appendAbbreviation(abbreviations, 0xa968, 0x1169, 0, "모래"); // ꥨᅩ
		appendAbbreviation(abbreviations, 0xa968, 0x1169, 0x11af, "몰래 "); // ꥨᅩᆯ
		appendAbbreviation(abbreviations, 0xa968, 0x116e, 0, "무리"); // ꥨᅮ
		appendAbbreviation(abbreviations, 0xa968, 0x116e, 0x11af, "물리"); // ꥨᅮᆯ
		appendAbbreviation(abbreviations, 0xa968, 0x1173, 0, "므로 "); // ꥨᅳ
		appendAbbreviation(abbreviations, 0xa968, 0x1175, 0, "미리 "); // ꥨᅵ
		appendAbbreviation(abbreviations, 0xa968, 0x1175, 0x11ab, "미련"); // ꥨᅵᆫ
		appendAbbreviation(abbreviations, 0xa968, 0x1175, 0x11af, "밀리"); // ꥨᅵᆯ
		appendAbbreviation(abbreviations, 0xa968, 0x117e, 0, "무려 "); // ꥨᅾ
		appendAbbreviation(abbreviations, 0xa968, 0x1189, 0, "마루"); // ꥨᆉ
		appendAbbreviation(abbreviations, 0xa969, 0x1160, 0x11a8, "비록 "); // ꥩᅠᆨ
		appendAbbreviation(abbreviations, 0xa969, 0x1160, 0x11af, "빨리 "); // ꥩᅠᆯ
		appendAbbreviation(abbreviations, 0xa969, 0x1160, 0x11b0, "바람직"); // ꥩᅠᆰ
		appendAbbreviation(abbreviations, 0xa969, 0x1160, 0x11b7, "바람"); // ꥩᅠᆷ
		appendAbbreviation(abbreviations, 0xa969, 0x1160, 0x11ba, "비롯"); // ꥩᅠᆺ
		appendAbbreviation(abbreviations, 0xa969, 0x1160, 0x11bc, "프랑스"); // ꥩᅠᆼ
		appendAbbreviation(abbreviations, 0xa969, 0x1161, 0, "바라"); // ꥩᅡ
		appendAbbreviation(abbreviations, 0xa969, 0x1165, 0x11b8, "법률"); // ꥩᅥᆸ
		appendAbbreviation(abbreviations, 0xa969, 0x1167, 0x11af, "별로 "); // ꥩᅧᆯ
		appendAbbreviation(abbreviations, 0xa969, 0x1169, 0, "바로 "); // ꥩᅩ
		appendAbbreviation(abbreviations, 0xa969, 0x1169, 0x11a8, "폭력"); // ꥩᅩᆨ
		appendAbbreviation(abbreviations, 0xa969, 0x1169, 0x11ab, "본래"); // ꥩᅩᆫ
		appendAbbreviation(abbreviations, 0xa969, 0x116c, 0, "피로"); // ꥩᅬ
		appendAbbreviation(abbreviations, 0xa969, 0x116c, 0x11a8, "올림픽"); // ꥩᅬᆨ
		appendAbbreviation(abbreviations, 0xa969, 0x116e, 0, "뿌리"); // ꥩᅮ
		appendAbbreviation(abbreviations, 0xa969, 0x1173, 0, "프로"); // ꥩᅳ
		appendAbbreviation(abbreviations, 0xa969, 0x1173, 0x11ba, "버릇"); // ꥩᅳᆺ
		appendAbbreviation(abbreviations, 0xa969, 0x1175, 0, "편리"); // ꥩᅵ
		appendAbbreviation(abbreviations, 0xa96d, 0x1160, 0x11ab, "전략"); // ꥭᅠᆫ
		appendAbbreviation(abbreviations, 0xa96d, 0x1160, 0x11b0, "침략"); // ꥭᅠᆰ
		appendAbbreviation(abbreviations, 0xa96d, 0x1160, 0x11b7, "처럼 "); // ꥭᅠᆷ
		appendAbbreviation(abbreviations, 0xa96d, 0x1160, 0x11bc, "종류"); // ꥭᅠᆼ
		appendAbbreviation(abbreviations, 0xa96d, 0x1160, 0x11c2, "저렇"); // ꥭᅠᇂ
		appendAbbreviation(abbreviations, 0xa96d, 0x1161, 0, "자료"); // ꥭᅡ
		appendAbbreviation(abbreviations, 0xa96d, 0x1161, 0x11bc, "자랑"); // ꥭᅡᆼ
		appendAbbreviation(abbreviations, 0xa96d, 0x1162, 0, "재료"); // ꥭᅢ
		appendAbbreviation(abbreviations, 0xa96d, 0x1163, 0x11bc, "차량"); // ꥭᅣᆼ
		appendAbbreviation(abbreviations, 0xa96d, 0x1165, 0, "처리"); // ꥭᅥ
		appendAbbreviation(abbreviations, 0xa96d, 0x1165, 0x11bc, "정리"); // ꥭᅥᆼ
		appendAbbreviation(abbreviations, 0xa96d, 0x1168, 0, "차례"); // ꥭᅨ
		appendAbbreviation(abbreviations, 0xa96d, 0x1169, 0x11a8, "오른쪽"); // ꥭᅩᆨ
		appendAbbreviation(abbreviations, 0xa96d, 0x1169, 0x11bc, "총리"); // ꥭᅩᆼ
		appendAbbreviation(abbreviations, 0xa96d, 0x116c, 0, "치료"); // ꥭᅬ
		appendAbbreviation(abbreviations, 0xa96d, 0x116e, 0, "주로 "); // ꥭᅮ
		appendAbbreviation(abbreviations, 0xa96d, 0x1173, 0, "적으로 "); // ꥭᅳ
		appendAbbreviation(abbreviations, 0xa96d, 0x1175, 0, "자리"); // ꥭᅵ
		appendAbbreviation(abbreviations, 0xa96d, 0x1175, 0x11ab, "진리"); // ꥭᅵᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11a8, "민족"); // ꥱᅠᆨ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11ab, "지만 "); // ꥱᅠᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11ad, "장면"); // ꥱᅠᆭ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11af, "정말"); // ꥱᅠᆯ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11b7, "마침 "); // ꥱᅠᆷ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11ba, "잘못"); // ꥱᅠᆺ
		appendAbbreviation(abbreviations, 0xa971, 0x1160, 0x11fa, "만족"); // ꥱᅠᇺ
		appendAbbreviation(abbreviations, 0xa971, 0x1161, 0, "마자 "); // ꥱᅡ
		appendAbbreviation(abbreviations, 0xa971, 0x1161, 0x11a8, "마지막"); // ꥱᅡᆨ
		appendAbbreviation(abbreviations, 0xa971, 0x1161, 0x11ab, "마찬가지"); // ꥱᅡᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1161, 0x11ad, "창문"); // ꥱᅡᆭ
		appendAbbreviation(abbreviations, 0xa971, 0x1161, 0x11af, "주말"); // ꥱᅡᆯ
		appendAbbreviation(abbreviations, 0xa971, 0x1161, 0x11bc, "전망"); // ꥱᅡᆼ
		appendAbbreviation(abbreviations, 0xa971, 0x1162, 0, "재미"); // ꥱᅢ
		appendAbbreviation(abbreviations, 0xa971, 0x1165, 0, "마저 "); // ꥱᅥ
		appendAbbreviation(abbreviations, 0xa971, 0x1165, 0x11a8, "무척 "); // ꥱᅥᆨ
		appendAbbreviation(abbreviations, 0xa971, 0x1165, 0x11ab, "먼저"); // ꥱᅥᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1166, 0, "문제"); // ꥱᅦ
		appendAbbreviation(abbreviations, 0xa971, 0x1166, 0x11b7, "문제점"); // ꥱᅦᆷ
		appendAbbreviation(abbreviations, 0xa971, 0x1167, 0, "며칠"); // ꥱᅧ
		appendAbbreviation(abbreviations, 0xa971, 0x1167, 0x11ab, "자면 "); // ꥱᅧᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1168, 0, "제목"); // ꥱᅨ
		appendAbbreviation(abbreviations, 0xa971, 0x1169, 0x11a8, "목적"); // ꥱᅩᆨ
		appendAbbreviation(abbreviations, 0xa971, 0x116c, 0, "조미료"); // ꥱᅬ
		appendAbbreviation(abbreviations, 0xa971, 0x116e, 0, "주민"); // ꥱᅮ
		appendAbbreviation(abbreviations, 0xa971, 0x116e, 0x11ab, "문자"); // ꥱᅮᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x116e, 0x11bc, "민중"); // ꥱᅮᆼ
		appendAbbreviation(abbreviations, 0xa971, 0x116f, 0, "주먹"); // ꥱᅯ
		appendAbbreviation(abbreviations, 0xa971, 0x116f, 0x11ab, "전문"); // ꥱᅯᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1175, 0, "마치 "); // ꥱᅵ
		appendAbbreviation(abbreviations, 0xa971, 0x1175, 0x11ab, "민주"); // ꥱᅵᆫ
		appendAbbreviation(abbreviations, 0xa971, 0x1175, 0x11af, "질문"); // ꥱᅵᆯ
		appendAbbreviation(abbreviations, 0xa971, 0x1189, 0, "마주 "); // ꥱᆉ
		appendAbbreviation(abbreviations, 0xa976, 0x1160, 0x11a8, "어리석"); // ꥶᅠᆨ
		appendAbbreviation(abbreviations, 0xa976, 0x1160, 0x11ab, "이론"); // ꥶᅠᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1160, 0x11af, "알려"); // ꥶᅠᆯ
		appendAbbreviation(abbreviations, 0xa976, 0x1160, 0x11b7, "이름"); // ꥶᅠᆷ
		appendAbbreviation(abbreviations, 0xa976, 0x1160, 0x11b8, "유럽"); // ꥶᅠᆸ
		appendAbbreviation(abbreviations, 0xa976, 0x1161, 0, "아라"); // ꥶᅡ
		appendAbbreviation(abbreviations, 0xa976, 0x1161, 0x11a8, "연락"); // ꥶᅡᆨ
		appendAbbreviation(abbreviations, 0xa976, 0x1161, 0x11ab, "이란"); // ꥶᅡᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1162, 0, "아래"); // ꥶᅢ
		appendAbbreviation(abbreviations, 0xa976, 0x1162, 0x11ab, "원래"); // ꥶᅢᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1165, 0, "러운 "); // ꥶᅥ
		appendAbbreviation(abbreviations, 0xa976, 0x1165, 0x11ab, "언론"); // ꥶᅥᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1165, 0x11af, "리얼"); // ꥶᅥᆯ
		appendAbbreviation(abbreviations, 0xa976, 0x1167, 0, "려운 "); // ꥶᅧ
		appendAbbreviation(abbreviations, 0xa976, 0x1167, 0x11ab, "여론"); // ꥶᅧᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1169, 0, "오래"); // ꥶᅩ
		appendAbbreviation(abbreviations, 0xa976, 0x1169, 0x11af, "올라"); // ꥶᅩᆯ
		appendAbbreviation(abbreviations, 0xa976, 0x116c, 0, "라디오"); // ꥶᅬ
		appendAbbreviation(abbreviations, 0xa976, 0x116d, 0, "러시아"); // ꥶᅭ
		appendAbbreviation(abbreviations, 0xa976, 0x116e, 0, "우리"); // ꥶᅮ
		appendAbbreviation(abbreviations, 0xa976, 0x116e, 0x11a8, "이룩"); // ꥶᅮᆨ
		appendAbbreviation(abbreviations, 0xa976, 0x116e, 0x11ab, "로운 "); // ꥶᅮᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x116f, 0x11ab, "원리"); // ꥶᅯᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1172, 0, "인류"); // ꥶᅲ
		appendAbbreviation(abbreviations, 0xa976, 0x1173, 0, "으로 "); // ꥶᅳ
		appendAbbreviation(abbreviations, 0xa976, 0x1173, 0x11ab, "얼른 "); // ꥶᅳᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x1173, 0x11b7, "음료"); // ꥶᅳᆷ
		appendAbbreviation(abbreviations, 0xa976, 0x1175, 0, "이래"); // ꥶᅵ
		appendAbbreviation(abbreviations, 0xa976, 0x1175, 0x11ab, "어린 "); // ꥶᅵᆫ
		appendAbbreviation(abbreviations, 0xa976, 0x119e, 0, "아름"); // ꥶᆞ
		appendAbbreviation(abbreviations, 0xa976, 0x11a2, 0, "어른"); // ꥶᆢ
		appendAbbreviation(abbreviations, 0xa976, 0xd7b0, 0, "으려"); // ꥶힰ
		appendAbbreviation(abbreviations, 0xa976, 0xd7c0, 0, "유리"); // ꥶퟀ
		appendAbbreviation(abbreviations, 0xa976, 0xd7c4, 0x11b7, "어려움"); // ꥶퟄᆷ

		return abbreviations;
	}

}
