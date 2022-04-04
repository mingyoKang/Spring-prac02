package spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
	// 메모리에 회원 데이터를 보관하므로 프로그램을 종료하면 저장한 모든 회원 데이터가 사라진다.
	// 프로그램을 종료해도 회원 데이터를 유지하려면 MySQL, 오라클과 같은 저장소에 보관해야 한다.
	
	private static long nextId = 0;
	
	private Map<String, Member> map = new HashMap<>();
	
	public Member selectByEmail(String email) {
		
		return map.get(email);
		
	}
	
	public void insert(Member member) {
		
		member.setId(++nextId);
		map.put(member.getEmail(), member);
		
	}
	
	public void update(Member member) {
		
		map.put(member.getEmail(), member);
		
	}
	
	public Collection<Member> selectAll(){
		
		return map.values();
		
	}

}
