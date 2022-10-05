package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {

		log.info("upload form");
	}

	// @PostMapping("/uploadFormAction")
	// public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
	//
	// for (MultipartFile multipartFile : uploadFile) {
	//
	// log.info("-------------------------------------");
	// log.info("Upload File Name: " +multipartFile.getOriginalFilename());
	// log.info("Upload File Size: " +multipartFile.getSize());
	//
	// }
	// }

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

		String uploadFolder = "C:\\upload";

		for (MultipartFile multipartFile : uploadFile) {

			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			} // end catch
		} // end for

	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {

		log.info("upload ajax");
	}

	// @PostMapping("/uploadAjaxAction")
	// public void uploadAjaxPost(MultipartFile[] uploadFile) {
	//
	// log.info("update ajax post.........");
	//
	// String uploadFolder = "C:\\upload";
	//
	// for (MultipartFile multipartFile : uploadFile) {
	//
	// log.info("-------------------------------------");
	// log.info("Upload File Name: " + multipartFile.getOriginalFilename());
	// log.info("Upload File Size: " + multipartFile.getSize());
	//
	// String uploadFileName = multipartFile.getOriginalFilename();
	//
	// // IE has file path
	// uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
	// 1);
	// log.info("only file name: " + uploadFileName);
	//
	// File saveFile = new File(uploadFolder, uploadFileName);
	//
	// try {
	//
	// multipartFile.transferTo(saveFile);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// } // end catch
	//
	// } // end for
	//
	// }

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	// @PostMapping("/uploadAjaxAction")
	// public void uploadAjaxPost(MultipartFile[] uploadFile) {
	//
	// String uploadFolder = "C:\\upload";
	//
	// // make folder --------
	// File uploadPath = new File(uploadFolder, getFolder());
	// log.info("upload path: " + uploadPath);
	//
	// if (uploadPath.exists() == false) {
	// uploadPath.mkdirs();
	// }
	// // make yyyy/MM/dd folder
	//
	// for (MultipartFile multipartFile : uploadFile) {
	//
	// log.info("-------------------------------------");
	// log.info("Upload File Name: " + multipartFile.getOriginalFilename());
	// log.info("Upload File Size: " + multipartFile.getSize());
	//
	// String uploadFileName = multipartFile.getOriginalFilename();
	//
	// // IE has file path
	// uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
	// 1);
	// log.info("only file name: " + uploadFileName);
	//
	// // File saveFile = new File(uploadFolder, uploadFileName);
	// File saveFile = new File(uploadPath, uploadFileName);
	//
	// try {
	//
	// multipartFile.transferTo(saveFile);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// } // end catch
	//
	// } // end for
	//
	// }

	// @PostMapping("/uploadAjaxAction")
	// public void uploadAjaxPost(MultipartFile[] uploadFile) {
	//
	// String uploadFolder = "C:\\upload";
	//
	// // make folder --------
	// File uploadPath = new File(uploadFolder, getFolder());
	// log.info("upload path: " + uploadPath);
	//
	// if (uploadPath.exists() == false) {
	// uploadPath.mkdirs();
	// }
	// // make yyyy/MM/dd folder
	//
	// for (MultipartFile multipartFile : uploadFile) {
	//
	// log.info("-------------------------------------");
	// log.info("Upload File Name: " + multipartFile.getOriginalFilename());
	// log.info("Upload File Size: " + multipartFile.getSize());
	//
	// String uploadFileName = multipartFile.getOriginalFilename();
	//
	// // IE has file path
	// uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
	// 1);
	// log.info("only file name: " + uploadFileName);
	//
	// UUID uuid = UUID.randomUUID();
	//
	// uploadFileName = uuid.toString() + "_" + uploadFileName;
	//
	// File saveFile = new File(uploadPath, uploadFileName);
	//
	// try {
	//
	// multipartFile.transferTo(saveFile);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// } // end catch
	//
	// } // end for
	//
	// }
	// 업로드 파일이 image 파일인지 확인
	private boolean checkImageType(File file) {

		try {
			//Files 클래스의 메소드를 이용해서 업로드 된 파일의 컨텐트 타입 추출
			String contentType = Files.probeContentType(file.toPath());
			//컨텐트 타입이 image 라는 글자로 시작되면 true 반환
			return contentType.startsWith("image");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// @PostMapping("/uploadAjaxAction")
	// public void uploadAjaxPost(MultipartFile[] uploadFile) {
	//
	// String uploadFolder = "C:\\upload";
	//
	// // make folder --------
	// File uploadPath = new File(uploadFolder, getFolder());
	// log.info("upload path: " + uploadPath);
	//
	// if (uploadPath.exists() == false) {
	// uploadPath.mkdirs();
	// }
	// // make yyyy/MM/dd folder
	//
	// for (MultipartFile multipartFile : uploadFile) {
	//
	// log.info("-------------------------------------");
	// log.info("Upload File Name: " + multipartFile.getOriginalFilename());
	// log.info("Upload File Size: " + multipartFile.getSize());
	//
	// String uploadFileName = multipartFile.getOriginalFilename();
	//
	// // IE has file path
	// uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
	// 1);
	// log.info("only file name: " + uploadFileName);
	//
	// UUID uuid = UUID.randomUUID();
	//
	// uploadFileName = uuid.toString() + "_" + uploadFileName;
	//
	// try {
	// File saveFile = new File(uploadPath, uploadFileName);
	// multipartFile.transferTo(saveFile);
	// // check image type file
	// if (checkImageType(saveFile)) {
	//
	// FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" +
	// uploadFileName));
	//
	// Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,
	// 100);
	//
	// thumbnail.close();
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } //end catch
	// } // end for
	//
	// }

	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	//ajax post upload 요청 처리 - 폼 데이터를 MultipartFile 배열로 바로 받아, 아무 어노테이션 없이...
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		//업로드된 파일 정보를 담아서 DB에 저장하기 위한 리스트
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		
		//파일 저장 경로 처리
		// 오늘 날짜를 경로 형태로 만들어 저장
		String uploadFolderPath = getFolder();
		// make folder --------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		// make yyyy/mm/dd folder
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		
		}
		// 파일명 처리 - 업로드 파일명에 UUID 붙여서 만듦
		for (MultipartFile multipartFile : uploadFile) {

			AttachFileDTO attachDTO = new AttachFileDTO();
			//업로드 파일 명 추출 저장
			String uploadFileName = multipartFile.getOriginalFilename();

			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name: " + uploadFileName);
			attachDTO.setFileName(uploadFileName);

			UUID uuid = UUID.randomUUID();

			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				File saveFile = new File(uploadPath, uploadFileName);
				// 해당 경로에 이미지 저장 완료 - temp에 저장된 파일을 해당 경로의 해당 파일명으로 이동
				multipartFile.transferTo(saveFile);

				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);

				// check image type file - 파일이 이미지이면 DTO에 true 표시하고 썸네일 만들어서 해당 경로에 저장
				if (checkImageType(saveFile)) {
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}

				// add to List
				list.add(attachDTO);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} // end for
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/display")	
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {

		log.info("fileName: " + fileName);

		File file = new File("c:\\upload\\" + fileName);

		log.info("file: " + file);

		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();

			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// @GetMapping(value = "/download", produces =
	// MediaType.APPLICATION_OCTET_STREAM_VALUE)
	// @ResponseBody
	// public ResponseEntity<Resource> downloadFile(String fileName) {
	//
	// log.info("download file: " + fileName);
	//
	// Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
	//
	// log.info("resource: " + resource);
	//
	// return null;
	// }

	// @GetMapping(value = "/download", produces =
	// MediaType.APPLICATION_OCTET_STREAM_VALUE)
	// @ResponseBody
	// public ResponseEntity<Resource> downloadFile(String fileName) {
	//
	// log.info("download file: " + fileName);
	//
	// Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
	//
	// log.info("resource: " + resource);
	//
	// String resourceName = resource.getFilename();
	//
	// HttpHeaders headers = new HttpHeaders();
	// try {
	// headers.add("Content-Disposition",
	// "attachment; filename=" + new String(resourceName.getBytes("UTF-8"),
	// "ISO-8859-1"));
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	// }

	// @GetMapping(value="/download" ,
	// produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	// @ResponseBody
	// public ResponseEntity<Resource>
	// downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
	//
	// Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
	//
	// if(resource.exists() == false) {
	// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// }
	//
	// String resourceName = resource.getFilename();
	//
	// HttpHeaders headers = new HttpHeaders();
	// try {
	//
	// boolean checkIE = (userAgent.indexOf("MSIE") > -1 ||
	// userAgent.indexOf("Trident") > -1);
	//
	// String downloadName = null;
	//
	// if (checkIE) {
	// downloadName = URLEncoder.encode(resourceName, "UTF8").replaceAll("\\+", "
	// ");
	// } else {
	// downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
	// }
	//
	// headers.add("Content-Disposition", "attachment; filename=" + downloadName);
	//
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	//
	// return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	// }

	// @GetMapping(value="/download" ,
	// produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	// @ResponseBody
	// public ResponseEntity<Resource>
	// downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
	//
	// Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
	//
	// if(resource.exists() == false) {
	// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// }
	//
	// String resourceName = resource.getFilename();
	//
	// //remove UUID
	// String resourceOriginalName =
	// resourceName.substring(resourceName.indexOf("_")+1);
	//
	// HttpHeaders headers = new HttpHeaders();
	// try {
	//
	// boolean checkIE = (userAgent.indexOf("MSIE") > -1 ||
	// userAgent.indexOf("Trident") > -1);
	//
	// String downloadName = null;
	//
	// if(checkIE) {
	// downloadName = URLEncoder.encode(resourceOriginalName,
	// "UTF8").replaceAll("\\+", " ");
	// }else {
	// downloadName = new
	// String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
	// }
	//
	// headers.add("Content-Disposition", "attachment; filename="+downloadName);
	//
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	//
	// return new ResponseEntity<Resource>(resource, headers,HttpStatus.OK);
	// }

	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {

		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);

		if (resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String resourceName = resource.getFilename();

		// remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

		HttpHeaders headers = new HttpHeaders();
		try {

			String downloadName = null;

			if ( userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			}else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName =  URLEncoder.encode(resourceOriginalName,"UTF-8");
			}else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			log.info("downloadName: " + downloadName);

			headers.add("Content-Disposition", "attachment; filename=" + downloadName);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	

	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {

		log.info("deleteFile: " + fileName);

		File file;

		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			if (type.equals("image")) {

				String largeFileName = file.getAbsolutePath().replace("s_", "");

				log.info("largeFileName: " + largeFileName);

				file = new File(largeFileName);

				file.delete();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}
	

}
