package huluwa.dexparser.format;

import huluwa.dexparser.base.Item;
import huluwa.dexparser.type.uLeb128;

public class encoded_annotation extends Item {

	public uLeb128 type_idx;
	public uLeb128 size;
	public annotation_element[] element;
	
	public encoded_annotation(byte[] data, int off) {
		super(data, off);
	}

	@Override
	public void parseData() {
		type_idx = this.cursor.nextuLeb128();
		size = this.cursor.nextuLeb128();
		element = new annotation_element[size.toInt()];
		for(int i = 0;i < size.toInt(); i++) 
		{
			element[i] = new annotation_element(this.cursor.getBytes(),this.cursor.getPos());
			this.cursor.belowMove(element[i].getLength());
		}
	}

	@Override
	public int getLength() {
		int len = 0;
		for(annotation_element e : element) 
		{
			len += e.getLength();
		}
		
		return type_idx.getLength() + size.getLength() + len;
	}

}
