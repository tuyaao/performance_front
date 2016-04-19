/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;

import com.appcloud.mysqldao.SumWeekCpuDao;
import com.appcloud.vm.common.HibernateSessionFactory;

//这个留下专门用来知道以后怎么写的
@Entity
@Table(name = "cpu_test_result_newweek", catalog = "appcloud_performance")
public class SumWeekCpu implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	private Integer uuid;
	private String companyname;
	private String time;
	private float totalTime;
	private Integer count;

	/** default constructor */
	public SumWeekCpu() {
	}

	/** minimal constructor */
	public SumWeekCpu(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SumWeekCpu(Integer id, Integer uuid, String companyname,
			String time, float totalTime, Integer count) {
		this.id = id;
		this.uuid = uuid;
		this.companyname = companyname;
		this.time = time;
		this.totalTime = totalTime;
		this.count = count;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "uuid")
	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	@Column(name = "companyname")
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	@Column(name = "time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "totalTime")
	public float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public static void main(String[] args){
		Session session = HibernateSessionFactory.getInstance().getSession();// 初始化HibernateSessionFactory,并预先获取session
		session.close();
		SumWeekCpuDao sumWeekCpuDao = new SumWeekCpuDao();
		List<SumWeekCpu> sumWeekCpuFind = sumWeekCpuDao.findByProperty("uuid", 19, "time", "87223");
	    if(sumWeekCpuFind.size() > 0){
	    	System.out.println("找到这个元素");
	    	sumWeekCpuDao.deleteById((SumWeekCpu)sumWeekCpuFind.get(0));
	    } 
		
	    SumWeekCpu sumWeekCpu = new SumWeekCpu();
	    sumWeekCpu.setCompanyname("公司");
	    sumWeekCpu.setCount(3);
	    sumWeekCpu.setTime("rfef");
	    sumWeekCpu.setTotalTime(34);
	    sumWeekCpu.setUuid(23);
	    sumWeekCpuDao.save(sumWeekCpu);
	}

}
