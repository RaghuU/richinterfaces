/*
 * package org.vunerability.demo.beans; public class PathTraversal {
 * 
 * 
 *//**
	 * http://localhost:8080/path_traversal/vul?filepath=../../../../../etc/passwd
	 *//*
		 * @GetMapping("/path_traversal/vul") public String getImage(String filepath)
		 * throws IOException { return getImgBase64(filepath); }
		 * 
		 * @GetMapping("/path_traversal/sec") public String getImageSec(String filepath)
		 * throws IOException { if (SecurityUtil.pathFilter(filepath) == null) {
		 * logger.info("Illegal file path: " + filepath); return
		 * "Bad boy. Illegal file path."; } return getImgBase64(filepath); }
		 * 
		 * private String getImgBase64(String imgFile) throws IOException {
		 * 
		 * logger.info("Working directory: " + System.getProperty("user.dir"));
		 * logger.info("File path: " + imgFile);
		 * 
		 * File f = new File(imgFile); if (f.exists() && !f.isDirectory()) { byte[] data
		 * = Files.readAllBytes(Paths.get(imgFile)); return new
		 * String(Base64.encodeBase64(data)); } else { return
		 * "File doesn't exist or is not a file."; } }
		 * 
		 * public static void main(String[] argv) throws IOException { String aa = new
		 * String(Files.readAllBytes(Paths.get("pom.xml")), StandardCharsets.UTF_8);
		 * System.out.println(aa); } }
		 */