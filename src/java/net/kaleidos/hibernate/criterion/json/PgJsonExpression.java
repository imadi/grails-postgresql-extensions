package net.kaleidos.hibernate.criterion.json;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.StringType;

public class PgJsonExpression implements Criterion {

    private static final long serialVersionUID = 8372629374639273L;

    private final String propertyName;
    private final String jsonOp;
    private final String jsonAttribute;
    private final String sqlOp;
    private final Object value;

    protected PgJsonExpression(String propertyName, String jsonOp, String jsonAttribute, String sqlOp, Object value) {
        this.propertyName = propertyName;
        this.jsonOp = jsonOp;
        this.jsonAttribute = jsonAttribute;
        this.sqlOp = sqlOp;
        this.value = value;
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return StringHelper.join(
                " and ",
                StringHelper.suffix(criteriaQuery.findColumns(propertyName, criteria), jsonOp + "'" + jsonAttribute + "' " + sqlOp + " ?")
        );
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[]{
            new TypedValue(new StringType(), value)
        };
    }
}
