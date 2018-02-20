/**
 * WebServicesBUAPPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package buap_api;

public interface WebServicesBUAPPortType extends java.rmi.Remote {

    /**
     * Nos da una lista de productos de cada categorÃ­a.
     */
    public buap_api.RespuestaConsulta getProd(java.lang.String user, java.lang.String pass, java.lang.String categoria) throws java.rmi.RemoteException;

    /**
     * Nos da los detalles de un producto en especifico.
     */
    public buap_api.RespuestaConsulta getDetails(java.lang.String user, java.lang.String pass, java.lang.String idPoducto) throws java.rmi.RemoteException;
}
