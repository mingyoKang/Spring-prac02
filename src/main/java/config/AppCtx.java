package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration
public class AppCtx {
	// 주입할 객체가 꼭 스프링 빈일 필요는 없다.
	// private MemberPrinter memberPrinter = new MemberPrinter(); 처럼
	// 빈으로 등록하지 않고 일반 객체로 생성해서 주입할 수도 있다.
	// 이때 차이점은 스프링 컨테이너가 객체를 관리하는지 여부이다.
	// 위와 같이 설정하면 MemberPrinter를 빈으로 등록하지 않았기에, 스프링 컨테이너에서 MemberPrinter를
	// 구할 수 없다.
	
	// 스프링 컨테이너는 자동 주입, 라이프사이클 관리 등 단순 객체 생성 외에 객체 관리를 위한
	// 다양한 기능을 제공하는데 빈으로 등록한 객체에만 기능을 적용한다.
	// 스프링 컨테이너가 제공하는 관리 기능이 필요없고 getBean() 메소드로 구할 필요가 없다면
	// 빈 객체로 꼭 등록해야하는 것은 아니다.
	
	// 최근에는 의존 자동 주입 기능을 프로젝트 전반에 걸쳐 사용하는 추세이기 때문에 의존 주입 대상은
	// 스프링 빈으로 등록하는 것이 일반적이다.

	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberRegisterService memberRegService() {
		return new MemberRegisterService(memberDao());
	}
	
	@Bean
	public ChangePasswordService changePwdService() {
		
		ChangePasswordService pwdService = new ChangePasswordService();
		// pwdService.setMemberDao(memberDao());
		// 의존을 주입하지 않아도 스프링이 @Autowired가 붙은 필드에
		// 해당 타입의 빈 객체를 찾아서 주입한다.
		// 따라서 상단의 코드는 필요없다.
		return pwdService;
		
		// 만약 @Autowired 애노테이션을 설정한 필드에 알맞은 빈 객체가 주입되지 않았다면
		// ChangePasswordService의 memberDao 필드는 null일 것이다. 
		// 그러면 암호 변경 기능을 실행할 때 NullPointerException이 발생한다.
		// 암호 변경 기능이 적상 동작했다면 @Autowired 애노테이션을 붙인 필드에 실제 MemberDao 타입의
		// 빈 객체가 주입되었음을 의미한다.
	}
	
	@Bean
	// @Qualifier("memberPrinter")
	// 자동 주입이 가능한 빈이 두 개 이상일 때 자동 주입할 빈을 지정할 수 있는 방법
	// @Qualifier 애노테이션을 사용하면 자동 주입 대상 빈을 한정할 수 있다.
	// @Qualifier 애노테이션 또한 필드와 메소드 모두에 적용할 수 있다.
	public MemberPrinter memberPrinter() {
		
		return new MemberPrinter();
		
	}
	
	// MemberSummaryPrinter 클래스 MemberPrinter 클래스를 상속했기 때문에 에러가 발생할 수 있다.
	// MemberSummaryPrinter 클래스는 MemberPrinter 타입에도 할당할 수 있으므로,
	// 스프링 컨테이너는 MemberPrinter 타입 빈을 자동 주입해야 하는 @Autowired 애노테이션 태그를 만나면
	// 자동 주입이 가능한 빈이 두 개 이상일 때 어떤 빈을 주입해야할지 알수 없다.
	// 그래서 @Autowired 애노테이션을 붙인 곳에 동일한 @Qualifier 애노테이션을 붙여 주입할 빈을 한정해야한다.
	@Bean
	public MemberSummaryPrinter memberSummaryPrinter() {
		return new MemberSummaryPrinter();
	}
	
	@Bean
	public MemberListPrinter memberListPrinter() {
		
		// return new MemberListPrinter(memberDao(), memberPrinter());
		return new MemberListPrinter();
	}
	
	@Bean
	public MemberInfoPrinter memberInfoPrinter() {
		
		// MemberInfoPrinter memberInfoPrinter = new MemberInfoPrinter();
		
		// 해당 메소드에 @Autowired 애노테이션을 붙임으로써 다음 코드가 필요없어졌다.
		// memberInfoPrinter.setMemberDao(memberDao());
		// memberInfoPrinter.setPrinter(memberPrinter());
		
		// 빈 객체의 메소드에 @Autowired 애노테이션을 붙이면 스프링은 해당 메소드를 호출한다.
		// 이때 메소드의 파라미터 타입에 해당하는 빈 객체를 찾아 인자로 주입한다.
		
		// @Autowired 애노테이션을 필드나 세터 메소드에 붙이면
		// 스프링은 타입이 일치하는 빈 객체를 찾아서 주입한다.
		
		//return memberInfoPrinter;
		return new MemberInfoPrinter();
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		
		VersionPrinter versionPrinter = new VersionPrinter();
		
		versionPrinter.setMajorVersion(3);
		versionPrinter.setMinorVersion(12);
		
		return versionPrinter;
	}
}
