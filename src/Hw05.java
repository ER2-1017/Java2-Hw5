public class Hw05 {
    static final int size = 10000000;
    static final int h = size / 2;
    public static void main (String[] args){
        float[] arr = new float[size];//массив единиц
        float[] arr1 = new float[size];//массив результвтов вычислений в одном потоке
        float[] arr2 = new float[h];//массив результатов вичислений в 2 потоках, 1-я половина
        float[] arr3 = new float[h];//массив результатов вичислений в 2 потоках, 2-я половина
        arrayWith1(arr);
        oneThread(arr, arr1);
        twoThreads(arr, arr1, arr2, arr3);

    }
    // Метод вычисляет массив с разбивкой на 2 потока, выводит затраченное время на каждый этап:
    private static void twoThreads(float[] arr, float[] arr1, float[] arr2, float[] arr3) {
        long start1 = System.currentTimeMillis();
        // Делим массив единиц на 2 части:
        System.arraycopy(arr,0, arr2,0,h);
        System.arraycopy(arr, h, arr3, 0, h);
        float[] a1 = new float[h]; // Массив с результатами вычислений, 1-я половина
        float[] a2 = new float[h]; // Массив с результатами вычислений, 2-я половина
        long stop1 = System.currentTimeMillis();
        System.out.println("Массив разбился на 2 части за "+(stop1-start1)+" мс.");

        long start2 = System.currentTimeMillis();
        new Thread(){
            public void run(){
                for (int i = 0; i<h; i++){
                   a1[i] = (float)(arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.arraycopy(a1,0, arr2,0, a1.length);
            }
        }.start();
        long stop2 = System.currentTimeMillis();
        System.out.println("1-я половина посчиталась за "+(stop2-start2)+" мс.");

        long start3 = System.currentTimeMillis();
        new Thread(){
            public void run(){
                for (int i = 0; i<h; i++){
                    a2[i] = (float)(arr3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.arraycopy(a2,0, arr3,0, a2.length);
            }
        }.start();
        long stop3 = System.currentTimeMillis();
        System.out.println("2-я половина посчиталась за "+(stop3-start3)+" мс.");
        long start4 = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr1, 0, arr, h, h);
        long stop4 = System.currentTimeMillis();
        System.out.println("Склейка произошла за "+(stop4-start4)+" мс.");
        System.out.println("Сложная функция в двух потоках построена за "+(stop4-start1)+" мс.");
    }

    // Метод вычисляет массив без разбивки и выводет затраченное время:
    private static void oneThread(float[] arr, float[] arr1) {
        long start = System.currentTimeMillis();
        for (int i = 0; i<size; i++){
            arr1[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long stop = System.currentTimeMillis();
        System.out.println("Сложная функция в одном потоке построена за "+(stop-start)+" мс.");
    }
    // Метод создает массив из единиц:
    private static void arrayWith1(float[] arr) {
        long start = System.currentTimeMillis();
        for (int i = 0; i<size; i++){
            arr[i] = 1;
        }
        long stop = System.currentTimeMillis();
        System.out.println("Массив из единиц построен за "+(stop-start)+" мс.");
    }

}
