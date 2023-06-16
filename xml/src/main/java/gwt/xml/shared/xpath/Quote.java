package gwt.xml.shared.xpath;

import gwt.xml.shared.expression.Lit;

public class Quote extends Lit {
    public Quote(Object value) {
        super(value);
    }

    @Override
    public StringBuilder append(StringBuilder target) {
        String source = String.valueOf(value);

        int s;
        int d;

        if ((s = source.indexOf('\'')) < 0)
            target.append('\'').append(source).append('\'');
        else if ((d = source.indexOf('"')) < 0)
            target.append('"').append(source).append('"');
        else {
            target.append("concat(");

            char p = s < d ? '\'' : '"';
            char m = p == '\'' ? '"' : '\'';
            int index = 0;
            for (int i = Math.min(s, d) + 1; i < source.length(); i++) {
                if (source.charAt(i) == m) {
                    if (index > 0)
                        target.append(',');

                    target.append(m).append(source, index, i).append(m);

                    char t = p;
                    p = m;
                    m = t;

                    index = i;
                }
            }

            if (index < source.length())
                target.append(',').append(m).append(source.substring(index)).append(m);

            target.append(')');
        }

        return target;
    }
}
