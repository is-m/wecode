package com.chinasoft.it.wecode.data.hibernate.tenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.util.LogUtils;

public class SchemaBasedMultiTenantConnectionProvider
		implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

	private static final long serialVersionUID = -7633505174318209340L;

	private static final Logger logger = LogUtils.getLogger();

	private DatasourceConnectionProviderImpl datasourceConnectionProvider = new DatasourceConnectionProviderImpl();

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return datasourceConnectionProvider.isUnwrappableAs(unwrapType);
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return datasourceConnectionProvider.unwrap(unwrapType);
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return datasourceConnectionProvider.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		datasourceConnectionProvider.closeConnection(connection);
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		logger.info("Multi Tenancy Use Datasource of {}",
				datasourceConnectionProvider.getDataSource().getClass().getName());
		final Connection connection = getAnyConnection();
		try {
			logger.info("Multi Tenancy Use schame of {}", tenantIdentifier);
			connection.createStatement().execute("USE " + tenantIdentifier);
		} catch (SQLException e) {
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		datasourceConnectionProvider.closeConnection(connection);
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return datasourceConnectionProvider.supportsAggressiveRelease();
	}

	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		Map settings = serviceRegistry.getService(ConfigurationService.class).getSettings();
		DataSource dataSource = (DataSource) settings.get(Environment.DATASOURCE);
		datasourceConnectionProvider.setDataSource(dataSource);
		datasourceConnectionProvider.configure(settings);
		logger.debug("connection provider:{}", datasourceConnectionProvider);
	}

}
