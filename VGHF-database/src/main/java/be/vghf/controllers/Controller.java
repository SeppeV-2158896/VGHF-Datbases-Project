package be.vghf.controllers;

public interface Controller {

    void setBaseController(BaseController baseController);

    void setListener(Controller controller);
}
