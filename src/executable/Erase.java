package executable;

import drawable.CadObjects;
import javafx.scene.input.InputEvent;
import kernel.CadDrawing;

public class Erase extends Commands {

	public static boolean invoke(InputEvent e) {
		System.out.println(CadDrawing.CURRENT_DRAWING.objects.size());
		for (int i = 0 ; i<CadDrawing.CURRENT_DRAWING.objects.size() ;i++){
			CadObjects o = CadDrawing.CURRENT_DRAWING.objects.get(i);
			if(o.isSelected()){
				System.out.println(o.isSelected());
				CadDrawing.CURRENT_DRAWING.remove(o);
			}
		}
		return false;
	}

	public static void abort() {
		// TODO Auto-generated method stub
		
	}

}
