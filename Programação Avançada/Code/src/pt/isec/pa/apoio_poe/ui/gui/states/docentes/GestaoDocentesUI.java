package pt.isec.pa.apoio_poe.ui.gui.states.docentes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.Manager;
import pt.isec.pa.apoio_poe.model.data.Docentes;
import pt.isec.pa.apoio_poe.model.fsm.GPEState;
import pt.isec.pa.apoio_poe.ui.gui.resources.FontManager;
import pt.isec.pa.apoio_poe.ui.gui.states.alunos.EditaAlunosForm;

import java.io.File;
import java.util.List;


public class GestaoDocentesUI extends BorderPane {
    Manager manager;
    Button btnAdiciona, btnRemove,btnEdita, btnImportar, btnExportar, btnSair;
    Label lbNameState,lbStatus;
    Font font;
    MenuBar menuBar;
    Menu mnFile,mnEdit;
    MenuItem mnSave, mnExit, mnAdicionar, mnRemover, mnImportar, mnExportar,mnEditar;
    TableView<Docentes> tableView;

    public GestaoDocentesUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createButtons() {
        font = FontManager.loadFont("BalsamiqSans-Regular.ttf",20);

        btnAdiciona = new Button("Adicionar");
        btnAdiciona.setFont(font);

        btnRemove = new Button("Remover");
        btnRemove.setFont(font);

        btnEdita = new Button("Editar");
        btnEdita.setFont(font);

        btnImportar = new Button("Importar");
        btnImportar.setFont(font);

        btnExportar = new Button("Exportar");
        btnExportar.setFont(font);

        btnSair = new Button("Voltar");

        btnSair.setFont(font);
    }

    private void createViews() {
        createButtons();

        //Parte de cima da interface
        BorderPane borderTop = new BorderPane();
        borderTop.setPrefWidth(654);
        borderTop.setPrefHeight(79);

        lbNameState = new Label("Docentes");
        lbNameState.setFont(FontManager.loadFont("BalsamiqSans-Regular.ttf",34));
        setMargin(lbNameState,new Insets(20,0,0,50));
        borderTop.setLeft(lbNameState);

        HBox hBoxButtomTop = new HBox(btnAdiciona,btnRemove,btnEdita,btnImportar,btnExportar);
        hBoxButtomTop.setPadding(new Insets(10,0,0,165));
        hBoxButtomTop.setSpacing(40);
        hBoxButtomTop.setPrefWidth(600);
        hBoxButtomTop.setPrefHeight(27);
        borderTop.setBottom(hBoxButtomTop);

        menuBar = Toolbar();
        borderTop.setTop(menuBar);

        //Parte do centro

        tableView = TableViewDocentes.createTableView();
        setMargin(tableView,new Insets(10,200,80,0));

        VBox vBoxButtonBottomSair = new VBox(btnSair);
        setMargin(vBoxButtonBottomSair,new Insets(0,0,10,50));
        vBoxButtonBottomSair.setAlignment(Pos.BOTTOM_LEFT);
        vBoxButtonBottomSair.setSpacing(10);


        lbStatus = new Label();
        lbStatus.setPrefWidth(Integer.MAX_VALUE);
        //lbStatus.setFont(new Font());
        lbStatus.setStyle("-fx-background-color: #c0c0c0");
        lbStatus.setFont(FontManager.loadFont("BalsamiqSans-Regular.ttf",20));
        lbStatus.setBorder(new Border(new BorderStroke(Color.DARKGRAY,BorderStrokeStyle.SOLID,CornerRadii.EMPTY
                ,BorderWidths.DEFAULT)));
        lbStatus.setPadding(new Insets(10));

        this.setTop(borderTop);
        this.setCenter(tableView);
        this.setLeft(vBoxButtonBottomSair);
        this.setBottom(lbStatus);

    }


    private MenuBar Toolbar() {
        mnFile = new Menu("File");
        mnAdicionar = new MenuItem("Adicionar");
        mnRemover = new MenuItem("Remover");
        mnImportar = new MenuItem("Importar");
        mnExportar = new MenuItem("Exportar");
        mnFile.getItems().addAll(mnAdicionar, mnRemover, mnImportar,mnExportar);

        mnEdit = new Menu("Edit");
        mnEditar = new MenuItem("Editar");
        mnEditar.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        mnSave = new MenuItem("Salvar");
        mnSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        mnExit = new MenuItem("Voltar");
        mnEdit.getItems().addAll(mnEditar,mnSave,mnExit);

        MenuBar menuBarAux = new MenuBar(mnFile,mnEdit);
        menuBarAux.setUseSystemMenuBar(true);
        return menuBarAux;
    }


    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> { update(); });
        btnAdiciona.setOnAction(event -> {

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAdiciona.getScene().getWindow());
            Scene scene = new Scene(new AddDocentesForm(stage,manager));
            stage.setScene(scene);
            stage.setTitle("Adicionar Docentes");
            stage.setMaxWidth(600);
            stage.setMaxHeight(500);
            stage.setMinWidth(600);
            stage.setMinHeight(500);
            stage.show();

        });
        mnAdicionar.setOnAction(actionEvent -> {
            btnAdiciona.fire();
        });
        btnSair.setOnAction(event -> {
            manager.voltar();
        });
        btnRemove.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnRemove.getScene().getWindow());
            Scene scene = new Scene(new RemoveDocentesForm(stage,manager));
            stage.setScene(scene);
            stage.setTitle("Remover Docentes");
            stage.setMaxWidth(800);
            stage.setMaxHeight(500);
            stage.setMinWidth(800);
            stage.setMinHeight(500);
            stage.show();
        });
        mnRemover.setOnAction(actionEvent -> {
            btnRemove.fire();
        });
        btnEdita.setOnAction(actionEvent -> {
            btnEdita.setOnAction(event -> {
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnRemove.getScene().getWindow());
                Scene scene = new Scene(new EditaDocentesForm(stage,manager));
                stage.setScene(scene);
                stage.setTitle("Editar docentes");
                stage.setMaxWidth(800);
                stage.setMaxHeight(500);
                stage.setMinWidth(800);
                stage.setMinHeight(500);
                stage.show();
            });
        });
        mnEditar.setOnAction(actionEvent -> {
            btnEdita.fire();
        });
        btnImportar.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File open...");
            fileChooser.setInitialDirectory(new File(".")); //Diretório corrente
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Docentes (*.csv)","*.csv")
            );
            File file = fileChooser.showOpenDialog(this.getScene().getWindow());
            if(file != null){
                manager.importFile(file);
            }
        });
        mnImportar.setOnAction(actionEvent -> {
            btnImportar.fire();
        });
        btnExportar.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File save...");
            fileChooser.setInitialDirectory(new File(".")); //Diretório corrente
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Docentes (*.csv)","*.csv")
            );
            File file = fileChooser.showSaveDialog(this.getScene().getWindow());
            if(file != null){
                manager.exportFile(file);
            }
        });
        mnExportar.setOnAction(actionEvent -> {
            btnExportar.fire();
        });
        mnSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File save...");
            fileChooser.setInitialDirectory(new File(".")); //Diretório corrente
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Salvar (*.dat)","*.dat")
            );
            File file = fileChooser.showSaveDialog(this.getScene().getWindow());
            if(file != null){
                manager.save(file);
            }
        });
    }

    private void update() {
        tableView.getItems().clear();
        List<Docentes> docentes = manager.getDocentes();
        if(docentes != null) {
            for (Docentes d : docentes)
                tableView.getItems().add(d);
        }
        lbStatus.setText(String.format("Alunos: %d Docentes: %d Propostas: %d Candidaturas: %d Atribuição de Propostas: %d Atribuição de Orientadores: %d",
                manager.nAlunos(),manager.nDocentes(),manager.nPropostas(),manager.nCandidaturas(),manager.nAtPropostas(),manager.nAtOrientadores()));
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,Insets.EMPTY)));

        if(manager.isFechado()) {
            btnAdiciona.setDisable(true);
            mnAdicionar.setDisable(true);
            btnRemove.setDisable(true);
            mnRemover.setDisable(true);
            btnEdita.setDisable(true);
            mnEditar.setDisable(true);
            btnImportar.setDisable(true);
            mnImportar.setDisable(true);
        }else {
            btnAdiciona.setDisable(false);
            mnAdicionar.setDisable(false);
            btnRemove.setDisable(false);
            mnRemover.setDisable(false);
            btnEdita.setDisable(false);
            mnEditar.setDisable(false);
            btnImportar.setDisable(false);
            mnImportar.setDisable(false);
        }

        if(manager.getState() != GPEState.GESTAO_DOCENTES){
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}

