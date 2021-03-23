/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.cate;

/**
 *
 * @author GF65
 */
public class CateDTO {

    private String id;
    private String name;

    public CateDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CateDTO() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
