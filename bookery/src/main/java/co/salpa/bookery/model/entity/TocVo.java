package co.salpa.bookery.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TocVo { //�������̺�
	
	//toc id�� �ڵ�����ai
	private int book_bid;
	private String toc;
}
