package visual_cryptography ;

import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 * @author Basel
**/
public class Visual_Cryptography extends Application {
     
    Image image ;
    Image ResultImage ;
    ImageView myImageView ;
    ImageView ViewResult1 ;
    ImageView ViewResult2 ;
    ImageView ViewResult3 ;
    Text ResaultName1 ;
    Text ResaultName2 ;
    Text ResaultName3 ;
    Text ListName ;
    Text ListName2 ;
    Text NameOfAlgo2 = new Text("") ;
    Text NameOfAlgo = new Text("") ;
    TextField Input_1 ;
    TextField Results ;
    TextField Num_of_shares_txtfield ;
    int Number_of_shares ;
    int Input ;
    int height_s ;
    int width_s ;
    int [][] out  ;
    double [][] out_2  ;
    int [][] Matrix ;
    List<File> list ;
    ListView<ImageView> MyShares = new ListView<>() ;
    ListView<ImageView> MyShares2 = new ListView<>() ;
    List<List<List<Integer>>> Num_of_shares_List = new ArrayList<List<List<Integer>>>() ;
    String[] tempArray ;
    String Path = "" ;
    String Input2 ;
    boolean [] Knowing ;
    BufferedImage output ;
    BufferedImage Result_Image ;
    File file ;
    
    public static String sortString(String inputString) {
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    } 
    
    public static  Vector<String> Clone (Set <String> S) {
        Vector <String> v = new Vector<> () ;
        for(String s : S)
            v.add(s) ;
        return v ;   
    }
    
    public static boolean Check (int Number, Vector <Integer> v ) {
       for(int i=0;i<v.size();i++)
           if(Number == v.get(i))
               return true ;
       return false ;
   }

    //**************************************************************************
    //            Create The White Pixels Permutation Array
    //**************************************************************************
    public static int [][] White_3 (int SubPixels, int Number_of_shares) {
        int Helper = SubPixels - Number_of_shares ;
        int[][] li = new int [Number_of_shares][SubPixels] ;
        for(int i=0;i<Number_of_shares;i++)
            for(int j=0;j<Helper;j++)
                li[i][j] = 0 ;
        for(int i=0;i<Number_of_shares;i++) {
            for(int j=Helper;j<SubPixels;j++) {
                if(i == j-Helper)
                    li[i][j] = 0 ;
                else li[i][j] = 1 ;
            }
        }  
        return li ;
    }
    
    //**************************************************************************
    //            Create The Black Pixels Permutation Array
    //**************************************************************************
    public static int [][] Black_3 (int SubPixels, int Number_of_shares) {
        int Helper = SubPixels - Number_of_shares ;
        int[][] li = new int [Number_of_shares][SubPixels] ;
        for(int i=0;i<Number_of_shares;i++)
            for(int j=0;j<Helper;j++)
                li[i][j] = 1 ;
        for(int i=0;i<Number_of_shares;i++) {
            for(int j=Helper;j<SubPixels;j++) {
                if(i == j-Helper)
                    li[i][j] = 1 ;
                else li[i][j] = 0 ;
            }
        }  
        return li ;
    }
    
    //**************************************************************************
    //            Create The White Pixels Permutation Array
    //**************************************************************************
    public static int [][] White_2 (int SubPixels) {
        int[][] li = new int [SubPixels][SubPixels] ;
        for(int i=0;i<SubPixels;i++) {
            for(int j=0;j<SubPixels;j++) {
                if(j == 0)
                    li[i][j] = 1 ;
                else li[i][j] = 0 ;
            }
        }
        return li ;
    }
    
    //**************************************************************************
    //            Create The Black Pixels Permutation Array
    //**************************************************************************
    public static int [][] Black_2 (int SubPixels) {
        int[][] li = new int [SubPixels][SubPixels] ;
        for(int i=0;i<SubPixels;i++) {
            for(int j=0;j<SubPixels;j++) {
                if(i == j)
                    li[i][j] = 1 ;
                else li[i][j] = 0 ;
            }
        }
        return li ;
    }
    
    //**************************************************************************
    //               Find The Perfect Shape For The Shares
    //**************************************************************************
    public  static int Perfect_Shape (int SubPixels) { 
        double res = Math.sqrt(SubPixels) ;
        if (res - Math.floor(res) == 0) 
            return (int)res;
        else {
            for(int i=1;i<Math.sqrt(SubPixels);i++)
                if(SubPixels%i == 0)
                    res = i ;
            return (int)res ;
        }
    }
    
    //**************************************************************************
    //       Load Pixels From The Uploaded Image Into A Choosen Array
    //**************************************************************************
    public static  int [][] LoadPixels (BufferedImage Image) {
        
        int height = Image.getHeight() ;
        int width = Image.getWidth() ;
        int[][] pixels = new int[height][width] ;
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                int pxl = Image.getRGB(j, i) ;
                int red = (pxl >>16) & 0xff ; 
                // black=1 and white=0
                if (red < 20 )
                    //the pixel is black
                    pixels[i][j] = 1 ;
                else
                    //the pixel is white
                    pixels [i][j] = 0 ;
            }
        }          
       return pixels ;
    }
    
    //**************************************************************************
    //                      Shuffle Method 
    //**************************************************************************
    public static void Shuffle(int[][] a, int height, int width) {
        Random random = new Random() ;
        int x = random.nextInt(10) ;
        for(int i=0;i<x;i++) {
            int y = random.nextInt(width) ;
            int z = random.nextInt(width) ;
            for(int j=0;j<height;j++) {
                int temp = a[j][y] ;
                a[j][y] = a[j][z] ;
                a[j][z] = temp ;
            }
        }
    }
    
    //**************************************************************************
    //                   Initialize An Array With Zero
    //**************************************************************************
    public static void Init_zero (int[][] a, int n, int m) {
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                a[i][j] = 0 ;
    }

    //**************************************************************************
    //                  Initialize An Array With False
    //*************************************************************************
    public static void Init_false (boolean [] a, int n) {
        for(int i=0;i<n;i++)            
            a[i] = false ;    
   }
    
    EventHandler<ActionEvent> ResetEvent
    = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent r) {
            Input_1.clear() ;
            Input_1.setPromptText("Enter How Many Shares You Want To Create") ;
            Num_of_shares_txtfield.clear(); 
            Num_of_shares_txtfield.setPromptText("Enter The Shares Numbers ( Space Seperated )") ;
            Num_of_shares_List.clear() ;
            MyShares.getItems().clear() ;
            myImageView.setImage(null) ;
            ViewResult1.setImage(null) ;
            ViewResult2.setImage(null) ;
            NameOfAlgo.setText("") ;
            ResaultName2.setText("Result") ;
        }
    };
    
    EventHandler<ActionEvent> Reset2_Event
    = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent r) {
            MyShares2.getItems().clear() ;
            ViewResult2.setImage(null) ;
            ViewResult3.setImage(null) ;
            ListName2.setText("") ;
        }
    };
 
  EventHandler<ActionEvent> Loading_Event
   = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent t) {
            myImageView.setImage(null) ;
            FileChooser fileChooser = new FileChooser();  
            file = fileChooser.showOpenDialog(null);
            image = new Image(file.toURI().toString());
            myImageView.setImage(image) ;
            Path = file.toURI().toString() ;
        }
    };
  
    EventHandler<ActionEvent> Loading2_Event
   = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent t) {
            MyShares2.getItems().clear() ;
            FileChooser fileChooser = new FileChooser() ; 
            list = fileChooser.showOpenMultipleDialog(null) ;
            for(int i=0;i<list.size();i++){
                Image m = new Image(list.get(i).toURI().toString()) ;
                ImageView n = new ImageView(m) ;
                n.setFitHeight(200) ;
                n.setFitWidth(280) ;
                MyShares2.getItems().add(n) ;
                ListName2.setText("The Loaded Shares") ;
            }
        }
    };
    
    EventHandler<ActionEvent> Two_Of_N_Algo_Event
    = new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent r) {
            try {
                Num_of_shares_txtfield.clear() ;
                Num_of_shares_txtfield.setPromptText("Enter The Shares Numbers ( Space Seperated )") ;
                Num_of_shares_List.clear() ;
                MyShares.getItems().clear() ;
                NameOfAlgo.setText("(2, " + Input_1.getText() + ")" ) ;
                Number_of_shares = Integer.parseInt(Input_1.getText()) ;
                BufferedImage imageB = ImageIO.read(file) ;
                int image_h = imageB.getHeight() ;
                int image_w = imageB.getWidth() ;

    //**************************************************************************
    //          Create The Pixels Matrix From The Uploaded Image
    //**************************************************************************
                int [][] pixels = new int [image_h][image_w] ;         
                pixels = LoadPixels (imageB) ;

    //**************************************************************************
    //                         The Main Input 
    //**************************************************************************
                int SubPixels = Number_of_shares ;
                
    //**************************************************************************
    //          Calculate The Height And The Width Of Each Share
    //**************************************************************************
                int Helper = Perfect_Shape(SubPixels) ;
                height_s = min(Helper, SubPixels/Helper) ;
                height_s *= image_h ;
                width_s = max(Helper, SubPixels/Helper) ; 
                width_s *= image_w ;

    //**************************************************************************
    //           Create And Initialize The Permutations Matrixes
    //**************************************************************************
                int [][] White = White_2 (SubPixels) ;
                int [][] Black = Black_2 (SubPixels) ;

    //**************************************************************************
    //               Create And Prepare a Matrix Of Shares
    //**************************************************************************
                for(int i=0;i<Number_of_shares;i++) 
                    Num_of_shares_List.add(new ArrayList<List<Integer>>()) ;

    //**************************************************************************
    //                    Create And Prepare Each Share
    //**************************************************************************
                for(int i=0;i<Number_of_shares;i++)
                    for(int j=0;j<height_s;j++) 
                        Num_of_shares_List.get(i).add(new ArrayList<Integer>()) ;

    //**************************************************************************
    //                          Fill The Shares
    //**************************************************************************
                int row_flag = 0 ;
                int col_flag = 0 ;
                Random rand = new Random() ;
                for(int k=0;k<image_h;k++) {
                    for(int m=0;m<image_w;m++) {
                        int random = rand.nextInt(SubPixels) ;
                        Shuffle(Black, SubPixels, SubPixels) ;
                        Shuffle(White, SubPixels, SubPixels) ;
                        for(int i=0;i<Number_of_shares;i++) {
                            int a = 0 ;
                            for(int j=row_flag%height_s;j<height_s/image_h+row_flag%height_s;j++) {
                                for(int t=col_flag%width_s;t<width_s/image_w+col_flag%width_s;t++) {
                                    if(pixels[k][m] == 1) {
                                            Num_of_shares_List.get(i).get(j).add(Black[random][a]) ;
                                            a++ ;
                                    }   else {
                                            Num_of_shares_List.get(i).get(j).add(White[random][a]) ;
                                            a++ ;
                                    }
                                }
                            }
                            random = (random+1)%SubPixels ;
                        }
                        col_flag += width_s/image_w ;
                    }
                    row_flag += height_s/image_h ;
                }  

    //**************************************************************************
    //                    Create An Image From Each Share                    
    //**************************************************************************
                String Share_Name = "Share_" ;
                int Share_Number = 1 ;
                for(int i=0;i<Number_of_shares;i++) {
                    BufferedImage share1 = new BufferedImage(width_s, height_s, BufferedImage.TYPE_INT_ARGB) ;
                    for(int j=0;j<height_s;j++) {
                        for(int t=0;t<width_s;t++) {
                            Color newColor ;
                            int a = Num_of_shares_List.get(i).get(j).get(t) ;
                            if(a == 1)
                                newColor = new Color(0, 0, 0) ;
                            else newColor = new Color(255, 255, 255) ;
                            share1.setRGB(t, j, newColor.getRGB()) ;
                        } 
                    }
                    new File("C:/My Shares").mkdir();
                    String Share_Final_Name = "C:/My Shares/"+Share_Name+Share_Number+".png" ;
                    File help = new File("C:/My Shares/"+Share_Name+Share_Number+".png") ;
                    ImageIO.write(share1, "png", new File(Share_Final_Name)) ;
                    Share_Number ++ ;
                    
                    Image m = new Image(help.toURI().toString()) ;
                    ImageView n = new ImageView(m) ;
                    n.setFitHeight(200) ;
                    n.setFitWidth(280) ;
                    MyShares.getItems().add(n) ;
                }
            }
            catch(IOException e) {} ;
        }
    };
    
    EventHandler<ActionEvent> Three_Of_N_Algo_Event
    = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent r) {
            try {
                Num_of_shares_txtfield.clear() ;
                Num_of_shares_txtfield.setPromptText("Enter The Shares Numbers ( Space Seperated )") ;
                Num_of_shares_List.clear() ;
                MyShares.getItems().clear() ;
                NameOfAlgo.setText("(3, " + Input_1.getText() + ")" ) ;
                Number_of_shares = Integer.parseInt(Input_1.getText()) ;
                BufferedImage imageB = ImageIO.read(file) ;
                int image_h = imageB.getHeight() ;
                int image_w = imageB.getWidth() ;

    //**************************************************************************
    //          Create The Pixels Matrix From The Uploaded Image
    //**************************************************************************
                int [][] pixels = new int [image_h][image_w] ;         
                pixels = LoadPixels (imageB) ;
                
    //**************************************************************************
    //                         The Main Input 
    //**************************************************************************
                int SubPixels = (2*Number_of_shares - 2) ;

    //**************************************************************************
    //          Calculate The Height And The Width Of Each Share
    //**************************************************************************
                int Helper = Perfect_Shape(SubPixels) ;
                height_s = min(Helper, SubPixels/Helper) ;
                height_s *= image_h ;
                width_s = max(Helper, SubPixels/Helper) ; 
                width_s *= image_w ;

    //**************************************************************************
    //           Create And Initialize The Permutations Matrixes
    //**************************************************************************
                int [][] White = White_3 (SubPixels, Number_of_shares) ;
                int [][] Black = Black_3 (SubPixels, Number_of_shares) ;

    //**************************************************************************
    //                Create And Prepare a Matrix Of Shares
    //**************************************************************************
                for(int i=0;i<Number_of_shares;i++) 
                    Num_of_shares_List.add(new ArrayList<List<Integer>>()) ;

    //**************************************************************************
    //                    Create And Prepare Each Share
    //**************************************************************************
                for(int i=0;i<Number_of_shares;i++)
                    for(int j=0;j<height_s;j++) 
                        Num_of_shares_List.get(i).add(new ArrayList<Integer>()) ;

    //**************************************************************************
    //                          Fill The Shares
    //**************************************************************************
                int Row_flag = 0 ;
                int Col_flag = 0 ;
                Random rand = new Random() ;
                for(int k=0;k<image_h;k++) {
                    for(int m=0;m<image_w;m++) {
                        int random = rand.nextInt(Number_of_shares) ;
                        Shuffle(Black, Number_of_shares, SubPixels) ;
                        Shuffle(White, Number_of_shares, SubPixels) ;
                        for(int i=0;i<Number_of_shares;i++) {
                            int a = 0 ;
                            for(int j=Row_flag%height_s;j<height_s/image_h+Row_flag%height_s;j++) {
                                for(int t=Col_flag%width_s;t<width_s/image_w+Col_flag%width_s;t++) {
                                    if(pixels[k][m] == 1) {
                                            Num_of_shares_List.get(i).get(j).add(Black[random][a]) ;
                                            a++ ;
                                    }   else {
                                            Num_of_shares_List.get(i).get(j).add(White[random][a]) ;
                                            a++ ;
                                    }
                                }
                            }
                            random = (random+1)%Number_of_shares ;
                        }
                        Col_flag += width_s/image_w ;
                    }
                    Row_flag += height_s/image_h ;
                }  

    //**************************************************************************
    //                    Create An Image From Each Share                    
    //**************************************************************************
                String Share_Name = "Share_" ;
                int Share_Number = 1 ;
                for(int i=0;i<Number_of_shares;i++) {
                    BufferedImage share1 = new BufferedImage(width_s, height_s, BufferedImage.TYPE_INT_ARGB) ;
                    for(int j=0;j<height_s;j++) {
                        for(int t=0;t<width_s;t++) {
                            Color newColor ;
                            int a = Num_of_shares_List.get(i).get(j).get(t) ;
                            if(a == 1)
                                newColor = new Color(0, 0, 0) ;
                            else newColor = new Color(255, 255, 255) ;
                            share1.setRGB(t, j, newColor.getRGB()) ;
                        } 
                    }
                    new File("C:/My Shares").mkdir();
                    String F_name = "C:/My Shares/"+Share_Name+Share_Number+".png" ;
                    File help = new File("C:/My Shares/"+Share_Name+Share_Number+".png") ;
                    ImageIO.write(share1, "png", new File(F_name)) ;
                    Share_Number ++ ;
                    
                    Image m = new Image(help.toURI().toString()) ;
                    ImageView n = new ImageView(m) ;
                    n.setFitHeight(200) ;
                    n.setFitWidth(280) ;
                    MyShares.getItems().add(n) ;
                }  
            }
            catch(IOException e) {} ;
        }
    };
    
    EventHandler<ActionEvent> ThirdAlgoEvent
    = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent r) {
            try {
                Num_of_shares_txtfield.clear() ;
                Num_of_shares_txtfield.setPromptText("Enter The Shares Numbers ( Space Seperated )") ;
                Num_of_shares_List.clear() ;
                MyShares.getItems().clear() ;
                NameOfAlgo.setText("(4, " + Input_1.getText() + ")" ) ;
                Number_of_shares = Integer.parseInt(Input_1.getText()) ;
                BufferedImage imageB = ImageIO.read(file) ;
                int image_h = imageB.getHeight() ;
                int image_w = imageB.getWidth() ;

    //**************************************************************************
    //          Create The Pixels Matrix From The Uploaded Image
    //**************************************************************************
                int [][] pixels = new int [image_h][image_w] ;         
                pixels = LoadPixels (imageB) ;
                
    //**************************************************************************
    //              Create White && Black Permutations Arrays
    //**************************************************************************
                Set <String> s1 = new HashSet <String> () ;
                Set <String> s2 = new HashSet <String> () ;
                String [] arr = new String[Number_of_shares] ;
                Vector <String> v_1 = new Vector<>() ;
                for (int i=1;i<=Number_of_shares;i++)
                    arr[i-1] = String.valueOf(i) ;
                for (int i=0;i<Number_of_shares;i++) {
                    s2.add(arr[i]) ;
                    s1.addAll(s2) ;
                    v_1 = Clone(s2) ;
                }
                int Help = 1 ;
                while(Help < Number_of_shares+1) {
                    s2.clear() ;
                    for(int i=0;i<v_1.size();i++) {
                        if(v_1.get(i).length() == Help) {
                            for(int j=0;j<Number_of_shares;j++) {
                                String Elm = "" ;
                                String [] Temp = v_1.get(i).split("") ;
                                for(int c=0;c<Temp.length;c++)
                                    s2.add(Temp[c]) ;
                                s2.add(arr[j]) ;
                                for (String s : s2)
                                    Elm += s ;
                                s1.add(Elm) ;
                                s2.clear() ;
                            }
                        }
                    }
                    v_1 = Clone(s1) ;
                    Help ++ ;
                }
                v_1 = Clone(s1) ;

                Vector < Vector <Integer> > v_2 = new Vector < Vector <Integer> >() ;
                for(int i=0;i<v_1.size();i++) {
                    v_2.add(new Vector<Integer>()) ;
                    String [] Temp = v_1.get(i).split("") ;
                    for(int j=0;j<Temp.length;j++) {
                        int w = Integer.parseInt(Temp[j]) ;
                        v_2.get(i).add(w) ;
                    }
                }
                v_2.add(new Vector<Integer>()) ;
                v_2.get(v_2.size()-1).add(-1) ;
                v_2.get(v_2.size()-1).add(-1) ;

                Vector < Vector <Integer> > odd = new Vector < Vector <Integer> >() ;
                Vector < Vector <Integer> > even = new Vector < Vector <Integer> >() ;
                for(int i=0;i<v_2.size();i++) {
                    if(v_2.get(i).size() % 2 == 0)
                        even.add(v_2.get(i)) ;
                    else
                        odd.add(v_2.get(i)) ;
                }

                double z = Math.pow(2, Number_of_shares - 1) ;
                int SubPixels = (int) z ;
                
                int [][] White_N = new int [Number_of_shares][SubPixels] ;
                int [][] Black_N = new int [Number_of_shares][SubPixels] ;
                for(int i=0;i<Number_of_shares;i++) {
                    for(int j=0;j<SubPixels;j++) {
                        if(Check(i+1, even.get(j)))
                            White_N [i][j] = 1 ;
                        else 
                            White_N [i][j] = 0 ;
                    }
                }

                for(int i=0;i<Number_of_shares;i++) {
                    for(int j=0;j<SubPixels;j++) {
                        if(Check(i+1, odd.get(j)))
                            Black_N [i][j] = 1 ;
                        else 
                            Black_N [i][j] = 0 ;
                    }
                }

    //**************************************************************************
    //          Calculate The Height And The Width Of Each Share
    //**************************************************************************
                int Helper = Perfect_Shape(SubPixels) ;
                height_s = min(Helper, SubPixels/Helper) ;
                height_s *= image_h ;
                width_s = max(Helper, SubPixels/Helper) ; 
                width_s *= image_w ;
                
    //**************************************************************************
    //                Create And Prepare a Matrix Of Shares
    //**************************************************************************
                for(int i=0;i<Number_of_shares;i++) 
                    Num_of_shares_List.add(new ArrayList<List<Integer>>()) ;

    //**************************************************************************
    //                    Create And Prepare Each Share
    //**************************************************************************
                for(int i=0;i<Number_of_shares;i++)
                    for(int j=0;j<height_s;j++) 
                        Num_of_shares_List.get(i).add(new ArrayList<Integer>()) ;

    //**************************************************************************
    //                          Fill The Shares
    //**************************************************************************
                int Row_flag = 0 ;
                int Col_flag = 0 ;
                Random rand = new Random() ;
                for(int k=0;k<image_h;k++) {
                    for(int m=0;m<image_w;m++) {
                        int random = rand.nextInt(Number_of_shares) ;
                        Shuffle(Black_N, Number_of_shares, SubPixels) ;
                        Shuffle(White_N, Number_of_shares, SubPixels) ;
                        for(int i=0;i<Number_of_shares;i++) {
                            int a = 0 ;
                            for(int j=Row_flag%height_s;j<height_s/image_h+Row_flag%height_s;j++) {
                                for(int t=Col_flag%width_s;t<width_s/image_w+Col_flag%width_s;t++) {
                                    if(pixels[k][m] == 1) {
                                            Num_of_shares_List.get(i).get(j).add(Black_N[random][a]) ;
                                            a++ ;
                                    }   else {
                                            Num_of_shares_List.get(i).get(j).add(White_N[random][a]) ;
                                            a++ ;
                                    }
                                }
                            }
                            random = (random+1)%Number_of_shares ;
                        }
                        Col_flag += width_s/image_w ;
                    }
                    Row_flag += height_s/image_h ;
                }  

    //**************************************************************************
    //                    Create An Image From Each Share                    
    //**************************************************************************
                String Share_Name = "Share_" ;
                int Share_Number = 1 ;
                for(int i=0;i<Number_of_shares;i++) {
                    BufferedImage share1 = new BufferedImage(width_s, height_s, BufferedImage.TYPE_INT_ARGB) ;
                    for(int j=0;j<height_s;j++) {
                        for(int t=0;t<width_s;t++) {
                            Color newColor ;
                            int a = Num_of_shares_List.get(i).get(j).get(t) ;
                            if(a == 1)
                                newColor = new Color(0, 0, 0) ;
                            else newColor = new Color(255, 255, 255) ;
                            share1.setRGB(t, j, newColor.getRGB()) ;
                        } 
                    }
                    new File("C:/My Shares").mkdir();
                    String F_name = "C:/My Shares/"+Share_Name+Share_Number+".png" ;
                    File help = new File("C:/My Shares/"+Share_Name+Share_Number+".png") ;
                    ImageIO.write(share1, "png", new File(F_name)) ;
                    Share_Number ++ ;
                    
                    Image m = new Image(help.toURI().toString()) ;
                    ImageView n = new ImageView(m) ;
                    n.setFitHeight(200) ;
                    n.setFitWidth(280) ;
                    MyShares.getItems().add(n) ;
                }  
            }
            catch(IOException e) {} ;
        }
    };
    
    EventHandler<ActionEvent> Decode_Event
    = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent r) {
            try {
                Input_1.clear() ;
                Input_1.setPromptText("Enter How Many Shares You Want To Create") ;
                Knowing = new boolean [Number_of_shares+1] ;
                Init_false(Knowing, Number_of_shares+1) ;
                output = new BufferedImage(width_s, height_s, BufferedImage.TYPE_INT_ARGB) ;
                out = new int[height_s][width_s] ;
                Init_zero (out, height_s, width_s) ;
                String Flag = "Share_" ;
                String Counter = "( " ;
                Input2 = Num_of_shares_txtfield.getText() ;
                String[] tempArray ;
                String delimiter = " " ;
                tempArray = Input2.split(delimiter) ;
                for(int i=0;i<tempArray.length;i++) {
                    int z = Integer.parseInt(tempArray[i]) ;
                    Knowing[z] = true ;
                }
                for(int i=0;i<Number_of_shares+1;i++) {
                    if(Knowing[i] == true) {
                        Counter += i+"+" ;
                        String Res = "C:/My Shares/"+Flag+i+".png" ;
                        Result_Image = ImageIO.read(new File(Res)) ;
                        Matrix = new int[Result_Image.getHeight()][Result_Image.getWidth()] ;
                        Matrix = LoadPixels (Result_Image) ;
                        for(int j=0;j<height_s;j++) {
                            for(int t=0;t<width_s;t++) {
                                if(Matrix[j][t] == 0 && out[j][t] == 0)
                                        out[j][t] = 0 ;
                                else out[j][t] = 1 ;
                            }
                        }
                    }
                }
                Counter = Counter.substring(0, Counter.length()-1) ;
                Counter += " )" ;
                
     //**************************************************************************
     //                  Convert The Matrix To Image
     //**************************************************************************
                for(int i=0;i<height_s;i++) {
                    for(int j=0;j<width_s;j++) {
                        Color co ;
                        if(out[i][j] == 1)
                            co = new Color(0, 0, 0) ;
                        else co = new Color(255, 255, 255) ;
                    output.setRGB(j, i, co.getRGB()) ;
                    }
                }
                ImageIO.write(output, "png", new File("C:/My Shares/Shares "+Counter+".png")) ;
                
                String z = "Shares "+Counter ;
                ResaultName2.setText(z) ;
                File help = new File("C:/My Shares/Shares "+Counter+".png") ;
                ResultImage = new Image(help.toURI().toString()) ;
                ViewResult1.setImage(ResultImage) ;
                ViewResult2.setImage(ResultImage) ;
                
            } catch(IOException e) {} ;
        }
    };
    
    EventHandler<ActionEvent> Decode2_Event
    = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent r) {
            try {
                ViewResult2.setImage(null) ;
                ViewResult3.setImage(null) ;
                Alert a = new Alert(Alert.AlertType.NONE) ; 
                BufferedImage imageB = ImageIO.read(list.get(0)) ;
                height_s = imageB.getHeight() ;
                width_s = imageB.getWidth() ;
                for(int i=1;i<list.size();i++){
                    BufferedImage imageC = ImageIO.read(list.get(i)) ;
                    int height_s2 = imageC.getHeight() ;
                    int width_s2 = imageC.getWidth() ;
                    if(height_s != height_s2 || width_s != width_s2) {
                        a.setAlertType(Alert.AlertType.ERROR); 
                        a.setContentText("Different dimensions") ;
                        a.show(); 
                        return ;
                    }
                }
                output = new BufferedImage(width_s, height_s, BufferedImage.TYPE_INT_ARGB) ;
                out = new int [height_s][width_s] ;
                Init_zero (out, height_s, width_s) ;
                for(int i=0;i<list.size();i++) {
                    Result_Image = ImageIO.read((list.get(i))) ;
                    Matrix = new int[Result_Image.getHeight()][Result_Image.getWidth()] ;
                    Matrix = LoadPixels (Result_Image) ;
                    for(int j=0;j<height_s;j++) {
                        for(int t=0;t<width_s;t++) {
                            if(Matrix[j][t] == 0 && out[j][t] == 0)
                                    out[j][t] = 0 ;
                            else out[j][t] = 1 ;
                        }
                    }
                }
     //**************************************************************************
     //                  Convert The Matrix To Image
     //**************************************************************************
                for(int i=0;i<height_s;i++) {
                    for(int j=0;j<width_s;j++) {
                        Color co ;
                        if(out[i][j] == 1)
                            co = new Color(0, 0, 0) ;
                        else co = new Color(255, 255, 255) ;
                    output.setRGB(j, i, co.getRGB()) ;
                    }
                }
                ImageIO.write(output, "png", new File("C:/My Shares/FinalRes.png")) ;
                ResaultName3.setText("Result") ;
                File help = new File("C:/My Shares/FinalRes.png") ;
                ResultImage = new Image(help.toURI().toString()) ;
                ViewResult3.setImage(ResultImage) ;
                ViewResult2.setImage(ResultImage) ;
                
            } catch(IOException e) {} ;
        }
    };
    
    //**************************************************************************
    //                           GUI Code 
    //**************************************************************************
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Pane pane1 = new Pane() ;
        Pane pane2 = new Pane() ;
        Pane pane3 = new Pane() ;
        
    //**************************************************************************
    //                          Reset Button
    //**************************************************************************
        Button Reset = new Button("Reset") ;
        Reset.setLayoutX(25) ;
        Reset.setLayoutY(300) ;
        Reset.setOnAction(ResetEvent) ;
        Reset.setId("Button") ;
        
    //**************************************************************************
    //                        Algorithms Title
    //**************************************************************************
        Text Algoritms_title = new Text() ;
        Algoritms_title.setText("Algorithms") ;
        Algoritms_title.setId("Title") ;
        HBox Algorithns_Title_Box = new HBox() ;
        Algorithns_Title_Box.getChildren().add(Algoritms_title) ;
        Algorithns_Title_Box.setAlignment(Pos.BOTTOM_CENTER) ;
        
    //**************************************************************************
    //                       Loading Image Button
    //**************************************************************************
        Button LoadImage = new Button("Load Image") ;
        LoadImage.setPrefSize(300, 20) ;
        LoadImage.setOnAction(Loading_Event) ;
        LoadImage.setId("Button") ;
        
    //**************************************************************************
    //                      Loading Image Frame 
    //**************************************************************************
        myImageView = new ImageView() ; 
        myImageView.setFitWidth(300) ;
        myImageView.setFitHeight(200) ;  
        
    //**************************************************************************
    //                    Loading Elements Containers
    //**************************************************************************  
        VBox image_view_frame_Box = new VBox() ;
        image_view_frame_Box.getChildren().addAll(myImageView) ;
        image_view_frame_Box.setAlignment(Pos.CENTER) ;
        image_view_frame_Box.setId("image_view_frame") ;
 
        VBox Loading_Elements  = new VBox(20) ;
        Loading_Elements.getChildren().addAll(image_view_frame_Box, LoadImage) ;
        Loading_Elements.setAlignment(Pos.CENTER) ;
        Loading_Elements.setPadding (new Insets (25, 10, 10, 25)) ;
        
    //**************************************************************************
    //                   Algorithm (Two Out Of N) Button  
    //**************************************************************************
        Button Two_Out_Of_N = new Button("(2, N) Scheme") ;
        Two_Out_Of_N.setPrefSize(120, 20) ;
        Two_Out_Of_N.setOnAction(Two_Of_N_Algo_Event) ;
        Two_Out_Of_N.setId("Button") ;
        
    //**************************************************************************
    //                   Algorithm (Three Out Of N) Button 
    //**************************************************************************
        Button Three_Out_Of_N = new Button("(3, N) Scheme") ;
        Three_Out_Of_N.setPrefSize(120, 20) ;
        Three_Out_Of_N.setOnAction(Three_Of_N_Algo_Event) ;
        Three_Out_Of_N.setId("Button") ;
        
    //**************************************************************************
    //                   Algorithm (N Out Of N) Button  
    //**************************************************************************
        Button N_Out_Of_N = new Button("(N, N) Scheme") ;
        N_Out_Of_N.setPrefSize(120, 20) ;
        N_Out_Of_N.setOnAction(ThirdAlgoEvent) ;
        N_Out_Of_N.setId("Button") ;

    //**************************************************************************
    //           Algorithms (buttons && Textfield && Title) Container
    //**************************************************************************
        HBox Algorithms_Buttons = new HBox(10) ;
        Algorithms_Buttons.getChildren().addAll(Two_Out_Of_N, Three_Out_Of_N, N_Out_Of_N) ;
        
        Input_1 = new TextField() ;
        Input_1.setPromptText("Enter How Many Shares You Want To Create") ;
        Input_1.setId("TextField") ;
        
        VBox Algo_btns_field = new VBox(20) ;
        Algo_btns_field.getChildren().addAll(Input_1, Algorithms_Buttons) ;
        
        VBox Algos_btns_txtfield_title = new VBox(92) ;
        Algos_btns_txtfield_title.getChildren().addAll(Algorithns_Title_Box, Algo_btns_field) ;
        Algos_btns_txtfield_title.setAlignment(Pos.CENTER) ;

    //**************************************************************************
    //                        Decoding Title 
    //**************************************************************************
        Text DecodindTitle = new Text() ;
        DecodindTitle.setText("Decoding") ;
        DecodindTitle.setId("Title") ;
        
        HBox Decoding_Title_Box = new HBox(30) ;
        Decoding_Title_Box.getChildren().add(DecodindTitle) ;
        Decoding_Title_Box.setAlignment(Pos.CENTER) ;
        
    //**************************************************************************
    //                        Decoding Button
    //**************************************************************************
        Button Decode = new Button("Decode") ;
        Decode.setPrefSize(350, 20) ;
        Decode.setOnAction(Decode_Event) ;
        Decode.setId("Button") ;
        
    //**************************************************************************
    //                   Number Of Shares Input Text Field         
    //**************************************************************************
        Num_of_shares_txtfield = new TextField() ;
        Num_of_shares_txtfield.setPromptText("Enter The Shares Numbers ( Space Seperated )") ;
        Num_of_shares_txtfield.setId("TextField") ;
        
    //**************************************************************************
    //                     Decoding Elements Containers
    //**************************************************************************
        VBox Decoding_txtfield_And_Btn = new VBox(20) ;
        Decoding_txtfield_And_Btn.getChildren().addAll(Num_of_shares_txtfield, Decode) ;
        Decoding_txtfield_And_Btn.setAlignment(Pos.BOTTOM_CENTER) ;
        
        VBox Decoding_Elements = new VBox(92) ;
        Decoding_Elements.getChildren().addAll(Decoding_Title_Box, Decoding_txtfield_And_Btn) ;
        Decoding_Elements.setAlignment(Pos.CENTER) ;
  
    //**************************************************************************
    //                    The Top Half Elements Container
    //************************************************************************** 
        HBox The_Top_Half = new HBox(50) ;
        The_Top_Half.getChildren().addAll(Loading_Elements, Algos_btns_txtfield_title, Decoding_Elements) ; 

    //**************************************************************************
    //                Decoding Result Name && View Frame 
    //**************************************************************************
        //In The First Scene
        ResaultName1 = new Text("Result") ;
        ResaultName1.setId("ResultName") ;
        ViewResult1 = new ImageView() ;
        ViewResult1.setFitWidth(300) ;
        ViewResult1.setFitHeight(230) ;
        
        //In The Second Scene
        ResaultName2 = new Text("Result") ;
        ResaultName2.setId("ResultName") ;
        ViewResult2 = new ImageView() ;
        ViewResult2.setFitWidth(900) ;
        ViewResult2.setFitHeight(500) ;
        
    //**************************************************************************
    //                    Decoding Result Container
    //**************************************************************************
        //In The First Scene
        HBox ViewBox1 = new HBox() ;
        ViewBox1.getChildren().add(ViewResult1) ;
        ViewBox1.setId("image_view_frame") ;
        
        //In The Second Scene
        HBox ViewBox2 = new HBox() ;
        ViewBox2.getChildren().add(ViewResult2) ;
        ViewBox2.setId("image_view_frame") ;
        
    //**************************************************************************
    //                      Switching Scenes Buttons
    //**************************************************************************
        //In The First Scene
        Button Better_View = new Button ("Better View") ;
        Better_View.setPrefSize(120, 30);
        Better_View.setId("Button") ;
        
        //In The Second Scene
        Button Lets_Back = new Button("<- Back To Scene 1") ;
        Lets_Back.setPrefSize(150, 40) ;
        Lets_Back.setLayoutX(170) ;
        Lets_Back.setLayoutY(600) ;
        Lets_Back.setId("Button") ;
        
        Button Lets_Back_3 = new Button("Back To Scene 3 ->") ;
        Lets_Back_3.setPrefSize(150, 40) ;
        Lets_Back_3.setLayoutX(920) ;
        Lets_Back_3.setLayoutY(600) ;
        Lets_Back_3.setId("Button") ;
        
    //**************************************************************************
    //                   Second Scene Elements Container
    //**************************************************************************
        VBox Back = new VBox(20) ;
        Back.getChildren().addAll(ViewBox2, ResaultName2) ;
        Back.setAlignment(Pos.CENTER) ;
        Back.setLayoutX(165) ;
        Back.setLayoutY(40) ;
        
    //**************************************************************************
    //           Result View Elements Containers In The First Scene
    //**************************************************************************
        HBox Better = new HBox(30) ;
        Better.getChildren().addAll(Better_View, ResaultName1) ;
        
        VBox viewRes = new VBox(15) ;
        viewRes.getChildren().addAll(ViewBox1, Better) ;
        viewRes.setAlignment(Pos.CENTER) ;
        viewRes.setLayoutX(25) ;
        viewRes.setLayoutY(350) ;
        
    //**************************************************************************
    //                        List Of Shares   
    //**************************************************************************
        MyShares.setOrientation(Orientation.HORIZONTAL) ;
        MyShares.setPrefHeight(230) ;
        MyShares.setPrefWidth(785) ;
        MyShares.setId("MySharesList") ;
        
    //**************************************************************************
    //               The Title Under The List Of Shares     
    //**************************************************************************
        NameOfAlgo.setId("ResultName") ;
        ListName = new Text("The Created Shares By ") ;
        ListName.setId("ResultName") ;
        
    //**************************************************************************
    //               List Of Shares Elements Containers   
    //**************************************************************************
        HBox MyList = new HBox() ;
        MyList.getChildren().add(MyShares) ;
        MyList.setHgrow(MyShares, Priority.ALWAYS) ;
        
        HBox ListTitle = new HBox() ;
        ListTitle.getChildren().addAll(ListName, NameOfAlgo) ;
        ListTitle.setAlignment(Pos.CENTER) ;
        
        VBox ListBox = new VBox(15) ;
        ListBox.getChildren().addAll(MyList, ListTitle) ;
        ListBox.setAlignment(Pos.CENTER) ;
        ListBox.setLayoutX(385) ;
        ListBox.setLayoutY(350) ;
        
    //**************************************************************************
    //                The Button That Switchs To Scene 3
    //**************************************************************************
        Button Go_To = new Button("Go To Scene 3 ->") ; 
        Go_To.setLayoutX(1030) ;
        Go_To.setLayoutY(600) ;
        Go_To.setId("Button") ;
        
    //**************************************************************************
    //                        Scene 3 Elements 
    //**************************************************************************
        MyShares2.setOrientation(Orientation.HORIZONTAL) ;
        MyShares2.setPrefHeight(230) ;
        MyShares2.setPrefWidth(900) ;
        MyShares2.setId("MySharesList") ;
          
        ListName2 = new Text("") ;
        ListName2.setId("ResultName") ;
        
        ResaultName3 = new Text("Result") ;
        ResaultName3.setId("ResultName") ;
        ViewResult3= new ImageView() ;
        ViewResult3.setFitWidth(420) ;
        ViewResult3.setFitHeight(250) ;
        
        Button Reset2 = new Button("Reset") ;
        Reset2.setOnAction(Reset2_Event) ;
        Reset2.setId("Button") ;

    //**************************************************************************
    //                    Scene 3 Elements Containers
    //**************************************************************************
        HBox ViewBox3 = new HBox() ;
        ViewBox3.getChildren().add(ViewResult3) ;
        ViewBox3.setId("image_view_frame") ;
        
        VBox Decode2_Box = new VBox(8) ;
        Decode2_Box.getChildren().addAll(ViewBox3, ResaultName3) ;
        Decode2_Box.setAlignment(Pos.CENTER) ;
        Decode2_Box.setLayoutX(630) ;
        Decode2_Box.setLayoutY(350) ;
        
        HBox MyList2 = new HBox() ;
        MyList2.getChildren().add(MyShares2) ;
        MyList2.setHgrow(MyShares2, Priority.ALWAYS) ;
        
        HBox ListTitle2 = new HBox(300) ;
        ListTitle2.getChildren().addAll(Reset2, ListName2) ;
        
        VBox ListBox2 = new VBox(15) ;
        ListBox2.getChildren().addAll(MyList2, ListTitle2) ;
        ListBox2.setAlignment(Pos.CENTER) ;
        ListBox2.setLayoutX(150) ;
        ListBox2.setLayoutY(30) ;
        
    //**************************************************************************
    //                          Scene 3 Buttons
    //**************************************************************************
        Button Loading_2 = new Button("Load Shares") ;
        Loading_2.setOnAction(Loading2_Event) ;
        Loading_2.setPrefSize(400, 20) ;
        Loading_2.setId("Button") ;
        
        Button Decode_2 = new Button("Decode") ;
        Decode_2.setOnAction(Decode2_Event) ;
        Decode_2.setPrefSize(400, 20) ;
        Decode_2.setId("Button") ;
        
        Button Better_View_2 = new Button("Better View") ;
        Better_View_2.setPrefSize(400, 20) ;
        Better_View_2.setId("Button") ;
        
        Button Lets_Back_2 = new Button("<-   Go To Scene 1") ;
        Lets_Back_2.setPrefSize(400, 20) ;
        Lets_Back_2.setId("Button") ;
        
        VBox Buttons = new VBox(20) ;
        Buttons.getChildren().addAll(Loading_2, Decode_2, Better_View_2) ;
        
        VBox All_Buttons = new VBox(60) ;
        All_Buttons.getChildren().addAll(Buttons, Lets_Back_2) ;
        All_Buttons.setLayoutX(150) ;
        All_Buttons.setLayoutY(350) ;

    //**************************************************************************
    //                       Last Steps In GUI
    //**************************************************************************    
        pane1.getChildren().addAll(The_Top_Half, viewRes, ListBox, Reset, Go_To) ;
        pane2.getChildren().addAll(Back, Lets_Back, Lets_Back_3) ;
        pane3.getChildren().addAll(All_Buttons, ListBox2, Decode2_Box) ;
        
        Scene scene1, scene2, scene3 ;
        scene1 = new Scene(pane1, 1200, 700) ;
        scene2 = new Scene(pane2, 1200, 700) ;
        scene3 = new Scene(pane3, 1200, 700) ;
        
        Better_View.setOnAction(e -> primaryStage.setScene(scene2)) ;
        Better_View_2.setOnAction(e -> primaryStage.setScene(scene2)) ;
        Lets_Back.setOnAction(e -> primaryStage.setScene(scene1)) ;
        Lets_Back_2.setOnAction(e -> primaryStage.setScene(scene1)) ;
        Lets_Back_3.setOnAction(e -> primaryStage.setScene(scene3)) ;
        Go_To.setOnAction(e -> primaryStage.setScene(scene3)) ;
        
        String css = this.getClass().getResource("Visual_Cryptography_CSS.css").toExternalForm() ; 
        scene1.getStylesheets().add(css) ;
        scene2.getStylesheets().add(css) ;
        scene3.getStylesheets().add(css) ;
         
        primaryStage.setTitle("Test Program") ;
        primaryStage.setScene(scene1) ;
        primaryStage.show() ;
    }
        
    public static void main(String[] args) {
        launch(args);
    }
}

