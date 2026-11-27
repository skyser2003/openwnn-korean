package me.blog.hgl1002.openwnn.hangul;

import android.util.SparseArray;

public final class Abbreviation {

    private Abbreviation() {
    }

    public static void appendAbbreviation(
            SparseArray<String> abbreviations,
            int cho,
            int jung,
            int jong,
            String output
    ) {
        abbreviations.append(
                makeAbbreviationKey(cho, jung, jong),
                output
        );
    }

    public static String getAbbreviation(
            SparseArray<String> abbreviations,
            int cho,
            int jung,
            int jong
    ) {
        if (abbreviations == null) {
            return null;
        }

        return abbreviations.get(
                makeAbbreviationKey(cho, jung, jong)
        );
    }

    private static int makeAbbreviationKey(
            int cho,
            int jung,
            int jong
    ) {
        return (getChoIndex(cho) << 16)
                | (getJungIndex(jung) << 8)
                | getJongIndex(jong);
    }

    private static int getChoIndex(int code) {
        // Hangul Choseong
        if (code >= 0x1100 && code <= 0x115f) {
            return code - 0x1100;
        }

        // Hangul Jamo Extended-A
        if (code >= 0xa960 && code <= 0xa97c) {
            return 0x60 + code - 0xa960;
        }

        throw new IllegalArgumentException(
                "Invalid choseong code: " + code
        );
    }

    private static int getJungIndex(int code) {
        // Hangul Jungseong Filler + Hangul Jungseong
        if (code >= 0x1160 && code <= 0x11a7) {
            return code - 0x1160;
        }

        // Hangul Jamo Extended-B (jungseong)
        if (code >= 0xd7b0 && code <= 0xd7c6) {
            return 0x48 + code - 0xd7b0;
        }

        throw new IllegalArgumentException(
                "Invalid jungseong code: " + code
        );
    }

    private static int getJongIndex(int code) {
        // No jongseong
        if (code == 0) {
            return 0;
        }

        // Hangul Jongseong
        if (code >= 0x11a8 && code <= 0x11ff) {
            return 1 + code - 0x11a8;
        }

        // Hangul Jamo Extended-B (jongseong)
        if (code >= 0xd7cb && code <= 0xd7fb) {
            return 1 + 0x58 + code - 0xd7cb;
        }

        throw new IllegalArgumentException(
                "Invalid jongseong code: " + code
        );
    }
}