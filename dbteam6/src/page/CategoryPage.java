package page;

import java.util.List;
import java.util.Scanner;

import category.CategoryDAO;
import category.CategoryDTO;

public class CategoryPage {
	public static int show() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("---------------------------------");
		System.out.println("카테고리 목록입니다....");
		System.out.println();

		CategoryDAO dao = new CategoryDAO();
		List<CategoryDTO> list = dao.getList();
		for (CategoryDTO dto : list) {
			System.out.println(dto.toString());
		}

		System.out.println("9. 이전 페이지로 돌아가기");
		System.out.println();
		System.out.print("입력(번호) : ");
		int choosen;
		try {
			choosen = sc.nextInt();
		}
		catch(Exception e) {
			sc.next();
			System.out.println("잘못된 입력 값 입니다. 다시 입력하세요.");
			return cons.ConsoleDB.CATEGORYPAGE;
		}
		
		switch(choosen) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			cons.ConsoleDB.choosenCategory = choosen;
			cons.ConsoleDB.choosenCategoryName = list.get(choosen-1).getCname();
			return cons.ConsoleDB.POSTPAGE;
		
		case 9:
			return cons.ConsoleDB.mainPage;
			
		default:
			System.out.println("다시 입력하세요.");
			return cons.ConsoleDB.CATEGORYPAGE;
		}
	}
}