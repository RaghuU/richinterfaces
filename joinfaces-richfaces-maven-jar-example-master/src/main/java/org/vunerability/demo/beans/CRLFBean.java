/*
 * package org.vunerability.demo.beans;
 * 
 * import javax.faces.context.ExternalContext; import
 * javax.faces.context.FacesContext; import javax.inject.Named; import
 * javax.servlet.http.Cookie; import javax.servlet.http.HttpServletResponse;
 * 
 * @Named public class CRLFBean {
 * 
 * FacesContext fc = FacesContext.getCurrentInstance(); HttpServletResponse
 * response = (HttpServletResponse) fc.getExternalContext().getResponse();
 * ExternalContext ec = fc.getExternalContext();
 * 
 * responsesetHeader("Content-Disposition", "attachment; filename=\"" + fileName
 * + "\""); // The Save As popup magic is done here. You can give it any file
 * name you want, this only won't work in MSIE, it will use current request URL
 * as file name instead.
 * 
 * response.addHeader("test1", request.getParameter("test1"));
 * response.setHeader("test2", request.getParameter("test2")); String author =
 * request.getParameter("test3"); Cookie cookie = new Cookie("test3", author);
 * response.addCookie(cookie); }
 */