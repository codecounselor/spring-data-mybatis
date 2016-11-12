package me.jclagache.data.mybatis.repository.support;

import me.jclagache.data.mybatis.repository.query.MyBatisPage;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * Created by Nate Good on 11/11/16.
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class } )})
public class SpringDataMyBatisPlugin implements Interceptor
{

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean pageable = false;
        Pageable pageableObject = null;
        MappedStatement stmt = null;
        Object[] args = invocation.getArgs();

        int i=0;
        for(Object o : args)
        {
            if(o != null)
            {
                if(Pageable.class.isAssignableFrom(o.getClass())) {
                    pageable = true;
                    pageableObject = (Pageable) o;
                    //TODO: read the pageable settings and assign them into RowBounds argument
                    args[i] = null;
                }
                if(o instanceof MappedStatement)
                {
                    stmt = (MappedStatement)o;
                }
            }
            i++;
        }

        Object ret = invocation.proceed();

        if(pageable && pageableObject != null && stmt != null) {
            int total  = -1;

            Executor exec = (Executor) invocation.getTarget();
            Statement sqlstmt = exec.getTransaction().getConnection().createStatement();


            // This is a bit shady. I might want to replace it by finding a similar function on the same
            //   mapper. For example: FunctionA => countOfFunctionA

            //Convert the select ... from ... statement to a count statement.
            //TODO: remove paginating LIMITS and such.
//            ResultSet s = sqlstmt.executeQuery(stmt.getSqlSource().getBoundSql(null).getSql().replace('\n', ' ')
//                .replaceFirst("[S|s][E|e][L|l][E|e][C|c][T\t]\\s+.*[F|f][R|r][O|o][M|m]","SELECT count(*) from "));
//
//            s.next();
            total = 10; //s.getShort(1);

            List results = (List)ret;
            MyBatisPage p = new MyBatisPage(results.subList(0,pageableObject.getPageSize()), pageableObject, total);
            return p;
        }

        return ret;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, (Interceptor) this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
