//package it.polimi.ingsw.View.GUI;
//
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
///**
// * Class used to set the scenes go to a specific scene
// */
//public class TransitionHandler {
//        private static Stage primaryStage;
//        private static Scene loadingScene;
//        private static Scene NicknameScene;
//        private static Scene MenuScene;
//
//
//        public static void setPrimaryStage(Stage primaryStage) {
//            TransitionHandler.primaryStage = primaryStage;
//        }
//
//        //scene setters
//        public static void setLoadingScene(Scene loadingScene) {
//            TransitionHandler.loadingScene = loadingScene;
//        }
//
//        public static void setNicknameScene(NicknameScene nicknameScene){
//            TransitionHandler.NicknameScene(); = new Scene(NicknameScene.getRoot());
//        }
//
//
//    public static void setMenuScene(MenuScene menuScene) {
//            TransitionHandler.MenuScene = new Scene(MenuScene.getRoot());
//        }
//
//
//        //go to...
//        private static void goTo(Scene scene){
//            Platform.runLater(() -> primaryStage.setScene(scene));
//        }
//
//        public static void toLoadingScene(){
//            goTo(loadingScene);
//        }
//
//        public static void toLoginScene(){
//            goTo(loginScene);
//        }
//
//        public static void toGodPowerScene(){
//            goTo(godPowerScene);
//        }
//
//}
