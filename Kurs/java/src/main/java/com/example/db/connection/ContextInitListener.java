package com.example.db.connection;

import com.example.db.dao.*;
import com.example.util.DbUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        try {
            Class.forName("org.h2.Driver"); // ensure driver available
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("H2 JDBC driver missing", e);
        }

        HikariConfig cfg = new HikariConfig();
        cfg.setDriverClassName("org.h2.Driver");
        cfg.setJdbcUrl("jdbc:h2:file:./data/snake;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=false");
        cfg.setUsername("sa");
        cfg.setPassword("");
        cfg.setMaximumPoolSize(8);
        HikariDataSource ds = new HikariDataSource(cfg);

        DbUtil.initSchema(ds);
        ctx.setAttribute("ds", ds);
        ctx.setAttribute("userDao", new UserDao(ds));
        ctx.setAttribute("leaderboardDao", new LeaderboardDao(ds));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariDataSource ds = (HikariDataSource) sce.getServletContext().getAttribute("ds");
        if (ds != null) ds.close();
    }
}