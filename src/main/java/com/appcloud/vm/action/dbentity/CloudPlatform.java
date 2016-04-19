/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

//把hibernate的注解去掉了，想看请到git上看
public class CloudPlatform implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;

	/** default constructor */
	public CloudPlatform() {
	}

	/** minimal constructor */
	public CloudPlatform(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public CloudPlatform(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;

	}

	/** property */
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
