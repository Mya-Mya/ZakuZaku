package ui;

public enum CutAspect {
    _16x9,
    _4x3,
    _1x1,
    nothing;

    @Override
    public String toString() {
        switch (this) {
            case _16x9:
                return "16x9";
            case _4x3:
                return "4x3";
            case _1x1:
                return "1x1";
            case nothing:
                return "未指定";
        }
        return super.toString();
    }

    double getRatio() {
        switch (this) {
            case _16x9:
                return 0.5625;
            case _4x3:
                return 0.75;
            case _1x1:
                return 1;

        }
        return 0;
    }

    static public CutAspect fromString(String s) {
        if (s.equals("16x9")) {
            return _16x9;
        }
        if (s.equals("4x3")) {
            return _4x3;
        }
        if (s.equals("1x1")) {
            return _1x1;
        }
        return nothing;
    }
}
