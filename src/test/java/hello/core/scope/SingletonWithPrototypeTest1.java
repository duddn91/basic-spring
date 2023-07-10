package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

public class SingletonWithPrototypeTest1 {

	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean pb1 = ac.getBean(PrototypeBean.class);
		pb1.addCount();
		Assertions.assertThat(pb1.getCount()).isEqualTo(1);

		PrototypeBean pb2 = ac.getBean(PrototypeBean.class);
		pb2.addCount();
		Assertions.assertThat(pb2.getCount()).isEqualTo(1);

	}

	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		Assertions.assertThat(count1).isEqualTo(1);

		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int count2 = clientBean2.logic();
		Assertions.assertThat(count2).isEqualTo(1);

	}

	@Scope("singleton")
	static class ClientBean {

		//  생성자 주입시 싱글톤 객체 내부에 최초 생성된 prototypeBean이 유지되므로 목적에 맞지 않음
//		private final PrototypeBean prototypeBean;

//		@Autowired
//		public ClientBean(PrototypeBean prototypeBean) {
//			this.prototypeBean = prototypeBean;
//		}

		// 1. ObjectProvider을 사용한 prototypeBean 생성, 스프링에 의존적
//		@Autowired
//		private ObjectProvider<PrototypeBean> prototypeBeanProvider;

		// 2. Provider를 사용한 prototypeBean 생성
		@Autowired
		private Provider<PrototypeBean> prototypeBeanProvider;

		public int logic() {
			PrototypeBean prototypeBean = prototypeBeanProvider.get();
			prototypeBean.addCount();
			return prototypeBean.getCount();
		}
	}


	@Scope("prototype")
	static class PrototypeBean {
		private int count = 0;

		public void addCount() {
			count++;
		}

		public int getCount() {
			return count;
		}

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}

		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}
	}
}
