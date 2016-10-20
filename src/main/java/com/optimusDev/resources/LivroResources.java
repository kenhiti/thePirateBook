package com.optimusDev.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amazonaws.util.Base64;
import com.optimusDev.domain.Arquivo;
import com.optimusDev.domain.Livro;
import com.optimusDev.service.ArquivoService;
import com.optimusDev.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroResources {
	
	@Autowired
	private LivroService service;
	
	@Autowired
	private ArquivoService arquivoService;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Livro>> listar(){
		List<Livro> livros = service.listar();
		
		for(Livro livro : livros){
			livro.setCapa(converter(livro.getCapa()));
		}
		return ResponseEntity.ok().body(livros);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Livro> buscar(@PathVariable("id") Long id){		
		return ResponseEntity.status(HttpStatus.OK).body(service.buscar(id));
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Livro livro){		
		livro = service.salvar(livro);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getIdLivro()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro){
		service.atualizar(livro);
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/file/pdf/{id}", method = RequestMethod.GET)	
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
			headers.add("Content-Disposition", "attachment; filename=file.pdf");
			headers.setContentLength(arquivo.getFile().length);
		}
		
		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.OK);
	}
	
	/*@CrossOrigin
	@RequestMapping(value = "/file/pdf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)		
	public void buscarPdfPorId(@PathVariable("id") Long id, HttpServletResponse response) throws IOException{		
		
		Arquivo arquivo = arquivoService.buscar(id);
		
		System.out.println("Arquivo.type : " +arquivo.getType());
		System.out.println("Arquivo.id : " +arquivo.getIdArquivo());		
		
		byte[] content = arquivo.getFile();		
		response.reset();
		response.setContentType("application/pdf");
		response.setContentLength(content.length);
		response.getOutputStream().write(content);
		response.getOutputStream().flush();
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		
		String filename = "file.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
		headers.setContentLength(arquivo.getFile().length);
		headers.add("Content-Disposition", "attachment; filename=file.pdf");		
	    
		return response;
	}*/
	
	@CrossOrigin
	@RequestMapping(value="/file/{type}",method = RequestMethod.POST)
	public ResponseEntity<?> salvarAvatar(@PathVariable("type")String type, @RequestBody MultipartFile file) throws IOException{
		Arquivo arquivo = new Arquivo();
		arquivo.setFile(file.getBytes());
		arquivo.setType(type);
		arquivo = arquivoService.salvar(arquivo);		
		return ResponseEntity.ok(converter(arquivo));
	}
	
	public Arquivo converter(Arquivo arquivo){
		StringBuilder sb = new StringBuilder();
		
		if(arquivo.getFile() != null){
			
			if(arquivo.getType().equals("Image")){
				sb.append("data:image/jpg/png;base64,");			
				String auxiliar = Base64.encodeAsString(arquivo.getFile());
				sb.append(auxiliar);
				arquivo.setBase64(sb.toString());
			}
		}
		return arquivo;
	}
}
