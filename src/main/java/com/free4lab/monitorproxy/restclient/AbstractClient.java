package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.Responses;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public abstract class AbstractClient<T> {

	private static final String BEGIN_TIME = "btime";
	private static final String END_TIME = "etime";

	private Logger logger = LoggerFactory.getLogger(getType());

	private WebResource resource;

	protected abstract Class<?> getType();// 返回的类型，可以是自定义热任何类型，只要和server的对应上即可

	protected abstract GenericType<List<T>> getGenericType();

	protected abstract String getByIdTimePath();

	// 另一个 WebResouce 类的方法 header() 可以给你的请求添加 HTTP 头部信息。
	public AbstractClient() {
		this(SettingLoader.SERVER_URL);
	}

	public AbstractClient(String baseURI) {
		ClientConfig cc = new DefaultClientConfig();
		Client c = Client.create(cc);
		resource = c.resource(baseURI);
	}

	// 这个其实和业务有关，应该再抽象一层
	protected List<T> findByIdTime(String id, Timestamp start, Timestamp end) {
		long startTime = System.currentTimeMillis();
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("id", id);
		params.add(BEGIN_TIME, start.getTime() + "");
		params.add(END_TIME, end.getTime() + "");
		List<T> result = (List<T>) getList(getByIdTimePath(), params);
	    return result;
	}

	// get方法，只根据路径获取返回值，返回值是预设类型
	@SuppressWarnings("unchecked")
	protected T get(String path) {
		try {
			return (T) resource.path(path).get(getType());// get(返回值)，T没有起到类型约束的作用，只是泛型
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("get Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.info("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error:" + ue.getMessage());
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	// get方法，根据路径和参数获取返回值，返回值是预设类型集合
	protected List<T> getList(String path, MultivaluedMap<String, String> params) {
		try {
			if (params == null) {
				params = new MultivaluedMapImpl();
			}
			logger.info("get list from path:" + path + " params:" + params);
			return (List<T>) resource.path(path).queryParams(params)// 指定查询参数：Create
																	// a new
																	// WebResource
																	// from this
																	// web
																	// resource
																	// with
																	// additional
																	// query
																	// parameters
																	// added to
																	// the URI
																	// of this
																	// web
																	// resource.
					.get(getGenericType());// 返回类集合
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.error("getlist Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
			} else if (ue.getResponse().getStatus() == 500) {
				logger.error("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.error("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	// 使用put方法，据路径和object更新，传输类型为APPLICATION_XML
	@SuppressWarnings("unchecked")
	protected T update(String path, Object cReq) {
		try {
			return (T) resource.path(path).type(MediaType.APPLICATION_XML)// type(设定传输类型)
					.put(getType(), cReq);// put(返回类型，请求数据)
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("update Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.info("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	// get方法，只根据路径获取返回值，返回值是任意类型，不一定是泛型所定义的类型
	protected Object otherGet(Class<?> type, String path) {
		try {
			return resource.path(path).get(type);// get(返回类型)
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("otherGet Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.info("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	// 使用post方法，据路径和object()传输数据，传输类型为APPLICATION_XML
	@SuppressWarnings("unchecked")
	protected T postWithRet(String path, Object cReq) {// post return 了，所以加一个RET
		try {
			return (T) resource.path(path).type(MediaType.APPLICATION_XML)
					.post(getType(), cReq);
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.error("postwithret Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.error("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.error("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.error("Unknow Error" + ue.getMessage());
				throw new RuntimeException("I do not know what is wrong");
				// return null;
			}
		}
	}

	// 使用post方法，据路径和object传输数据，传输类型为APPLICATION_XML，返回值为List
	@SuppressWarnings("unchecked")
	protected List<T> postWithRetList(String path, Object cReq) {
		try {
			return (List<T>) resource.path(path)
					.type(MediaType.APPLICATION_XML).post(getType(), cReq);
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("postWithRetList Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.info("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return null;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
				// return null;
			}
		}
	}

	// 使用post方法，据路径和object传输数据，传输类型为APPLICATION_XML，返回值为传输是否成功
	protected boolean postWithoutRet(String path, Object req) {
		try {
			resource.path(path).type(MediaType.APPLICATION_XML).post(req);
			return true;
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("postWithoutRet Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return false;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.info("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return false;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	// delete方法
	protected boolean delete(String path) {
		try {
			logger.info("in api-client delete image path:" + path);
			resource.path(path).delete();
			return true;
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("delete Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return false;
				// throw new ItemNotFoundException();
			} else if (ue.getResponse().getStatus() == Responses.CONFLICT) {
				logger.info("Input error: "
						+ ue.getResponse().getEntity(String.class));
				return false;
				// throw new OperationFailedException();
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	// get方法，根据路径和参数获取个数
	protected Long count(String path, MultivaluedMap<String, String> params) {
		try {
			if (params == null) {
				params = new MultivaluedMapImpl();
			}
			return Long.valueOf(resource.path(path).queryParams(params)
					.get(String.class));
		} catch (UniformInterfaceException ue) {
			if (ue.getResponse().getStatus() == Responses.NOT_FOUND) {
				logger.info("count Item Not Found: "
						+ ue.getResponse().getEntity(String.class));
				return null;
			} else if (ue.getResponse().getStatus() == 500) {
				logger.info("Internal Server Error: "
						+ ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				logger.info("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

}
