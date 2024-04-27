/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models.usuario;

import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.Genero;

/**
 *
 * @author juan
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuarios_id_usuario_seq")
    @SequenceGenerator(name="usuarios_id_usuario_seq", sequenceName="usuarios_id_usuario_seq", initialValue=1, allocationSize=1)
    @Column(name="id_usuario")
    private long idUsuario;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_genero")
    private Genero genero;
    @Column()
    private String nombre;
    @Column()
    private Date fechaNacimiento;
    @Column()
    private String email;
    @Column()
    private String password;
    @Column()
    private String telefono;
    @OneToOne(cascade={CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_direccion")
    private Direccion direccion;
    @Column()
    private String urlFoto;
    
    public long getId() {
        return this.idUsuario;
    }

    public void setId(long id) {
        this.idUsuario=id;
    }

    public Genero getGenero() {
        return this.genero;
    }

    public void setGenero(Genero genero) {
        this.genero=genero;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento=fechaNacimiento;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono=telefono;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion=direccion;
    }

    public String getURLFoto() {
        return this.urlFoto;
    }
    
    public void setURLFoto(String urlFoto) {
        this.urlFoto=urlFoto;
    }
}
