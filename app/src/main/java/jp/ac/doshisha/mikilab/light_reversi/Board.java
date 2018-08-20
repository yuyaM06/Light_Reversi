package jp.ac.doshisha.mikilab.light_reversi;

public class Board {
    public static final int BOARD_SIZE = 6;
    public static final int NONE = 0;
    public static final int BLACK = -1;
    public static final int WHITE = 1;
    public static final int NEXT = 9;


    int b_w = -1;   // -1:黒の手番，1:白の手番
    int st[][] = {{0, 0, 0, 0, 0, 0},
                  {0, 0, 0, 0, 0, 0},
                  {0, 0, 1, -1, 0, 0},
                  {0, 0, -1, 1, 0, 0},
                  {0, 0, 0, 0, 0, 0},
                  {0, 0, 0, 0, 0, 0}};   // 盤面の状態（0:コマが置かれてない，-1:黒，1:白）

    int n[] = new int [9];   // 指定された位置の各方向に対する反転できるコマの数
                             //    [0] : 上方向
                             //    [1] : 斜め右上方向
                             //    [2] : 右方向
                             //    [3] : 斜め右下方向
                             //    [4] : 下方向
                             //    [5] : 斜め左下方向
                             //    [6] : 左方向
                             //    [7] : 斜め左上方向
                             //    [8] : 全体

    int num[] = new int[2];  //    [0]:盤面上の黒の数,
                             //    [1]:盤面上の白の数

    int mode = 1;       //1:Me先攻，2:CPU先攻    先攻→黒，後攻→白
    int flag = -1;

    public void Board(){

    }

    //
    // ボタンがタップされたときの処理
    //
    public int actionPerformed(int ppos[])   //押下されたボタンの座標を引数にする
    {
        // タップされたボタンを特定
        int k[] = {-1, -1};
        int judge = 0;

        k[0] = ppos[0];
        k[1] = ppos[1];

        //反転できるコマを探す
        r_check(k);
        // 反転するコマがない場合
        if (n[8] <= 0) {
            System.out.println("あなたの番ですが，");
            System.out.println("そこへはコマを置けません．");
            return -9;
        }
        // 反転するコマがある場合
        else {
            judge = set(k);
            if(judge == 9) return 9;
            return 1;
        }
    }
    //
    // コマの操作
    //
    int set(int k[]) //computerの順番のとき，return 9をする.勝敗が決まれば99を返す
    {
        // 反転
        reverse(k);
        b_w = -b_w;
        // コマの数を数え，勝敗決定のチェック
        int b = 0, w = 0, total = 0;
        for (int i1 = 0; i1 < 6; i1++) {
            for (int i2 = 0; i2 < 6; i2++) {
                if (st[i1][i2] != 0) {
                    total++;
                    if (st[i1][i2] < 0)
                        b++;
                    else
                        w++;
                }
            }
        }
         // 勝敗決定
         if (total == 36) {
            //if (gp.mp.method > 0) {
                if (b == w) {
                    System.out.println("引き分けです．");
                    flag = 0;
                }
                else if (b > w && mode == 1 || b < w && mode == 2){
                    System.out.println("あなたの勝ちです．");
                    flag = 1;
                }
                else{
                    System.out.println("コンピュータの勝ちです．");
                    flag = 9;
                }
             return 99;
         }
         // 勝負継続
         else {
            // スキップのチェック
            boolean sw = false;
            for (int i1 = 0; i1 < 6 && !sw; i1++) {
                for (int i2 = 0; i2 < 6 && !sw; i2++) {
                    k[0] = i1;
                    k[1] = i2;
                    r_check(k);
                    if (n[8] > 0)
                        sw = true;
                }
            }
            // スキップの場合
            if (!sw) {//modeの吟味b_wも
                b_w = -b_w;
                System.out.println("コマを置けないため，スキップし，");
                if(mode == 1 && b_w < 0){
                    System.out.println("あなたの番です．");
                    return 1;
                }
                else if(mode == 2 && b_w < 0){
                    System.out.println("コンピュータの番です．");
                    return 9;
                }

                //if(mode == 1)mode = 2;
                //else mode = 1;
                //return 9;
            }
//                if (mode == 1 && b_w < 0) {
//                    System.out.println("あなた（黒）の番です．");
//                    return -1;
//                }
//                else if (mode == 2 && b_w > 0) {
//                    System.out.println("あなた（白）の番です．");
//                    return 1;
//                }
//                else {
//                    System.out.println("コンピュータの番です．");
//                    return 9;
//                }

            // 次の手
            if (mode == 1 && b_w < 0) {
                System.out.println("あなた（黒）の番です．");
                return -1;
            }
            else if (mode == 2 && b_w > 0) {
                System.out.println("あなた（白）の番です．");
                return 1;
            }
            else {
                System.out.println("コンピュータの番です．");
                //k = computer();
                //set(k);
                return 9;
            }
         }
            // 対人間
    //            else {
    //                // スキップの場合
    //                if (!sw) {
    //                    gp.ta.append("コマを置けないため，スキップし，\n");
    //                    b_w = -b_w;
    //                }
    //                // 次の手
    //                if (b_w < 0)
    //                    gp.ta.append("黒の番です．\n");
    //                else
    //                    gp.ta.append("白の番です．\n");
    //            }
    }

    //
    // k[0] 行 k[1] 列に黒または白（ b_w ）のコマを置いた場合，反転できるコマを探す
    //
    void r_check(int k[])
    {
        int d[][] = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        n[8] = 0;

        if (st[k[0]][k[1]] == 0) {
            for (int i1 = 0; i1 < 8; i1++) {
                int m1 = k[0], m2 = k[1];
                n[i1] = 0;
                int s = 0;   // 0:開始，1:カウント，2:カウント終了，3:反転不可能
                int ct = 0;
                while (s < 2) {
                    m1 += d[i1][0];
                    m2 += d[i1][1];
                    if (m1 >= 0 && m1 < 6 && m2 >= 0 && m2 < 6) {
                        if (st[m1][m2] == 0)
                            s = 3;
                        else if (st[m1][m2] == b_w) {
                            if (s == 1)
                                s = 2;
                            else
                                s = 3;
                        } else {
                            s = 1;
                            ct++;
                        }
                    } else
                        s = 3;

                }
                if (s == 2) {
                    n[8] += ct;
                    n[i1] = ct;
                }
            }
        }
        //System.out.println(ct);
    }

    //
    // k[0] 行 k[1] 列に黒または白（ b_w ）のコマを置いた場合におけるコマの反転
    //
    void reverse(int k[])
    {
        int d[][] = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int i1 = 0; i1 < 8; i1++) {
            int m1 = k[0], m2 = k[1];
            for (int i2 = 0; i2 < n[i1]; i2++) {
                m1 += d[i1][0];
                m2 += d[i1][1];
                st[m1][m2]    = b_w;
            }
        }
        st[k[0]][k[1]]    = b_w;
    }

    //
    // コンピュータが置くコマの場所を決定
    //
    int [] computer() {
        int k[] = new int[2];
        int kk[] = new int[2];
        int mx[] = new int[9];
        mx[8] = 0;
        for (int i1 = 0; i1 < 6; i1++) {
            for (int i2 = 0; i2 < 6; i2++) {
                kk[0] = i1;
                kk[1] = i2;
                r_check(kk);
                if (n[8] > mx[8]) {
                    k[0] = kk[0];
                    k[1] = kk[1];
                    for (int i3 = 0; i3 < 9; i3++)
                        mx[i3] = n[i3];
                }
            }
        }
        for (int i1 = 0; i1 < 9; i1++)
            n[i1] = mx[i1];
        return k;

    }
    void count(){
        int bnum = 0;
        int wnum = 0;

        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++) {
                if (st[i][j] == -1) bnum++;
                if (st[i][j] == 1) wnum++;
            }
        }
        num[0] = bnum;
        num[1] = wnum;
    }

    void init(){
        for(int i=0; i<6; i++)
            for (int j = 0; j < 6; j++)
                st[i][j] = 0;

        st[2][2] = 1; st[3][3] = 1;
        st[2][3] = -1; st[3][2] = -1;

        b_w = -b_w; //担当コマの交代
    }

}
