package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
    	
    	pocho();
    }
    
    private static void fechaActual() {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   	 
        String date = dateFormat.format(new Date());
 
        System.out.println(date); 
    }
    
    private static void crearFichero() {
		File f=new File("file.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private static void escribirEnFicheroLineas() throws IOException {
    	BufferedWriter output = new BufferedWriter(new FileWriter("file.txt"));
    	output.write("me cago en to");
    	output.newLine();
    	output.write("asies");
    	output.flush();
    }
    
    private static void mostrarOpciones(ArrayList<String>tos) {
    	Object[] options = tos.toArray();
    	
    	Object seleccion = JOptionPane.showInputDialog(
    			   null, "Seleccione opcion", "Selector de opciones",
    			   JOptionPane.QUESTION_MESSAGE,null, 
    			   options, 
    			   "opcion 1");

    			System.out.println("El usuario ha elegido "+tos.indexOf(seleccion));
    }
    
    private static String rellenar() {
    	String seleccion = JOptionPane.showInputDialog(null, "titulo", JOptionPane.QUESTION_MESSAGE);
    	return seleccion;
    }
    
    private static void pocho() {
    	Random r = new Random();
    	if(r.nextBoolean()) {
    		System.out.println("a");
        	System.out.println("b");
        	System.out.println("c");
        	return;
    	}
    		System.out.println("d");
        	System.out.println("e");
        	System.out.println("f");
    	
    	
    }
    
    //1-bug, 2-dark, 3-dragon, 4-electric, 5-fairy, 
    //6-fighting, 7-fire, 8-flying, 9-ghost, 10-grass, 
    //11-ground, 12-ice, 13-normal, 14-poison, 15-psychic, 16-rock, 17-steel, 18-water
    private double getMultiplayerEffectivity(int moveType, int t1, int t2) {
    	double toret=1;
    	
    	switch(moveType) {
    		//tipo bicho;
    		case 1:
    			if(t1==10||t1==15||t1==2)toret=2;
    			else if(t1==17||t1==9||t1==5||t1==7||t1==6||t1==14||t1==8)toret=0.5;
    			else toret=1;
    
    			break;
    		//tipo siniestro	
    		case 2:
    			if(t1==9||t1==15)toret=2;
    			else if(t1==5||t1==6||t1==2)toret=0.5;
    			else toret=1;
    			
    			break;
    		//tipo dragon	
    		case 3:
    			if(t1==3)toret=2;
    			else if(t1==17)toret=0.5;
    			else if(t1==5) toret=0;
    			else toret=1;
    			
    			break;
    		//tipo electrico	
    		case 4:
    			if(t1==8||t1==18)toret=2;
    			else if(t1==3||t1==4||t1==10)toret=0.5;
    			else if(t1==11)toret=0;
    			else toret=1;
    			
    			break;
    		//fairy	
    		case 5:
    			if(t1==17||t1==7||t1==14) toret=0.5;
    			else if(t1==3||t1==6||t1==2)toret=2;
    			else toret=1;
    			
    			break;
    			
    		//lucha	
    		case 6:
    			if(t1==17||t1==12||t1==13||t1==16||t1==2)toret=2;
    			else if(t1==1||t1==5||t1==15||t1==14||t1==8)toret=0.5;
    			else if(t1==9)toret=0;
    			else toret=1;
    			break;
    			
    		//fuego	
    		case 7:
    			if(t1==17||t1==1||t1==12||t1==10)toret=2;
    			else if(t1==18||t1==3||t1==7||t1==16)toret=0.5;
    			else toret=1;
    			
    			break;
    		//volador	
    		case 8:
    			if(t1==1||t1==6||t1==10)toret=2;
    			if(t1==17||t1==4||t1==16)toret=0.5;
    			else toret=1;
    			
    			break;
    		//fantasma	
    		case 9:
    			if(t1==9||t1==15)toret=2;
    			else if(t1==2)toret=0.5;
    			else if(t1==13)	toret=0;
    			else toret=1;
    			break;
    			
    		//planta	
    		case 10:
    			if(t1==18||t1==16||t1==11)toret=2;
    			else if(t1==17||t1==1||t1==3||t1==7||t1==10||t1==14||t1==8)toret=0.5;
    			else toret=1;
    			break;
    			
    		//tierra	
    		case 11:
    			if(t1==17||t1==4||t1==7||t1==16||t1==14)toret=2;
    			else if(t1==1||t1==10)toret=0.5;
    			else toret=1;
    			break;
    		//hielo
    		case 12:
    			if(t1==3||t1==10||t1==11||t1==8)toret=2;
    			else if(t1==17||t1==18||t1==7||t1==12)toret=0.5;
    			else toret=1;
    			break;
    		//normal	
    		case 13:
    			if(t1==17||t1==16)toret=0.5;
    			else if(t1==9)toret=0;
    			else toret=1;
    			
    			break;
    		//veneno	
    		case 14:
    			if(t1==10||t1==5)toret=2;
    			else if(t1==9||t1==16||t1==11||t1==14)toret=0.5;
    			else if(t1==17)toret=0;
    			else toret=1;
    			break;
    		//psiquico	
    		case 15:
    			if(t1==17||t1==15)toret=0.5;
    			else if(t1==6||t1==14)toret=2;
    			else if(t1==2)toret=0;
    			else toret=1;
    			break;
    		
    		//roca
    		case 16:
    			if(t1==1||t1==7||t1==12||t1==8)toret=2;
    			else if(t1==17||t1==6||t1==11)toret=0.5;
    			else toret=1;
    			break;
    		//acero	
    		case 17:
    			if(t1==17||t1==18||t1==4||t1==7)toret=0.5;
    			else if(t1==5||t1==12||t1==16)toret=2;
    			else toret=1;
    			break;
    		//agua	
    		case 18:
    			if(t1==18||t1==3||t1==10)toret=0.5;
    			else if(t1==7||t1==16||t1==11)toret=2;
    			else toret=1;
    			
    			break;
    	}
    	
    	
    	//tipo2
    	if(t2!=0) {
	    	switch(moveType) {
			//tipo bicho;
			case 1:
				if(t2==10||t2==15||t2==2)toret=2;
				else if(t2==17||t2==9||t2==5||t2==7||t2==6||t2==14||t2==8)toret=0.5;
				else toret=1;
	
				break;
			//tipo siniestro	
			case 2:
				if(t2==9||t2==15)toret=2;
				else if(t2==5||t2==6||t2==2)toret=0.5;
				else toret=1;
				
				break;
			//tipo dragon	
			case 3:
				if(t2==3)toret=2;
				else if(t2==17)toret=0.5;
				else if(t2==5) toret=0;
				else toret=1;
				
				break;
			//tipo electrico	
			case 4:
				if(t2==8||t2==18)toret=2;
				else if(t2==3||t2==4||t2==10)toret=0.5;
				else if(t2==11)toret=0;
				else toret=1;
				
				break;
			//fairy	
			case 5:
				if(t2==17||t2==7||t2==14) toret=0.5;
				else if(t2==3||t2==6||t2==2)toret=2;
				else toret=1;
				
				break;
				
			//lucha	
			case 6:
				if(t2==17||t2==12||t2==13||t2==16||t2==2)toret=2;
				else if(t2==1||t2==5||t2==15||t2==14||t2==8)toret=0.5;
				else if(t2==9)toret=0;
				else toret=1;
				break;
				
			//fuego	
			case 7:
				if(t2==17||t2==1||t2==12||t2==10)toret=2;
				else if(t2==18||t2==3||t2==7||t2==16)toret=0.5;
				else toret=1;
				
				break;
			//volador	
			case 8:
				if(t2==1||t2==6||t2==10)toret=2;
				if(t2==17||t2==4||t2==16)toret=0.5;
				else toret=1;
				
				break;
			//fantasma	
			case 9:
				if(t2==9||t2==15)toret=2;
				else if(t2==2)toret=0.5;
				else if(t2==13)	toret=0;
				else toret=1;
				break;
				
			//planta	
			case 10:
				if(t2==18||t2==16||t2==11)toret=2;
				else if(t2==17||t2==1||t2==3||t2==7||t2==10||t2==14||t2==8)toret=0.5;
				else toret=1;
				break;
				
			//tierra	
			case 11:
				if(t2==17||t2==4||t2==7||t2==16||t2==14)toret=2;
				else if(t2==1||t2==10)toret=0.5;
				else toret=1;
				break;
			//hielo
			case 12:
				if(t2==3||t2==10||t2==11||t2==8)toret=2;
				else if(t2==17||t2==18||t2==7||t2==12)toret=0.5;
				else toret=1;
				break;
			//normal	
			case 13:
				if(t2==17||t2==16)toret=0.5;
				else if(t2==9)toret=0;
				else toret=1;
				
				break;
			//veneno	
			case 14:
				if(t2==10||t2==5)toret=2;
				else if(t2==9||t2==16||t2==11||t2==14)toret=0.5;
				else if(t2==17)toret=0;
				else toret=1;
				break;
			//psiquico	
			case 15:
				if(t2==17||t2==15)toret=0.5;
				else if(t2==6||t2==14)toret=2;
				else if(t2==2)toret=0;
				else toret=1;
				break;
			
			//roca
			case 16:
				if(t2==1||t2==7||t1==12||t1==8)toret=2;
				else if(t1==17||t1==6||t1==11)toret=0.5;
				else toret=1;
				break;
			//acero	
			case 17:
				if(t1==17||t1==18||t1==4||t1==7)toret=0.5;
				else if(t1==5||t1==12||t1==16)toret=2;
				else toret=1;
				break;
			//agua	
			case 18:
				if(t1==18||t1==3||t1==10)toret=0.5;
				else if(t1==7||t1==16||t1==11)toret=2;
				else toret=1;
				
				break;
	    	}
    	}
    	
    	
    }
    
    
    
    
}