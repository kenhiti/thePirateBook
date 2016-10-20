package com.optimusDev.resources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optimusDev.domain.Arquivo;
import com.optimusDev.service.ArquivoService;

@RestController
@RequestMapping("/arquivos")
public class ArquivoResources {
	
	@Autowired
	private ArquivoService arquivoService;
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)	
	public ResponseEntity<InputStreamResource> buscarPdfPorId(@PathVariable("id") Long id){		
		
		Arquivo arquivo = arquivoService.buscar(id);
		
		System.out.println("Arquivo.type : " +arquivo.getType());
		System.out.println("Arquivo.id : " +arquivo.getIdArquivo());
		
		InputStream is = null;		
		InputStreamResource inputStreamResource = null;
		HttpHeaders headers = null;
		
		if("Pdf".equals(arquivo.getType())){
			System.out.println("EH PDF SIM....");
			is = new ByteArrayInputStream(arquivo.getFile());		
			
			inputStreamResource = new InputStreamResource(is);			
		    
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);	
			headers.add("Content-Disposition", "inline; filename=file.pdf");
			headers.setContentLength(arquivo.getFile().length);
		}
		
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.OK);
	}
}
