package com.dam.servicios.servicios;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.dam.servicios.servicios.almacenamiento.FileSystemStorageService;
import com.dam.servicios.servicios.almacenamiento.StorageFileNotFoundException;
import com.dam.servicios.servicios.almacenamiento.StorageService;
import com.dam.servicios.servicios.almacenamiento.usuarioClient;
import com.dam.servicios.servicios.clases.Usuario;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
public class FileUploadController {

    private final StorageService storageService;
    @Autowired
    usuarioClient usuarioClient;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/mock")
    public String mock(){
        return usuarioClient.greeting();

    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {


        storageService.store(file);
        ArrayList<Usuario> u = this.procesar(file);
        System.out.println(u);
        for(Usuario usu : u ){
            usuarioClient.json(usu);
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    //procesar
    public ArrayList<Usuario> procesar(MultipartFile a) throws IOException {
        ArrayList<Usuario> listJson;
        listJson = new ArrayList<>();


        InputStream file = a.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.createSheet("a");
        sheet = workbook.getSheetAt(0);
        Row row;
        Usuario usu = new Usuario();
        System.out.println(sheet.getLastRowNum());
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // points to the starting of excel i.e excel first row
            row = sheet.getRow(i); // sheet number

            String id;
            if (row.getCell(0) == null) {
                id = "0";
            } else {
                id = row.getCell(0).toString();

            }

            String name;
            if (row.getCell(1) == null) {
                name = "null";
            } // suppose excel cell is empty then its set to 0 the variable
            else {
                name = row.getCell(1).toString();
            } // else copies cell data to name variable

            String email;
            if (row.getCell(2) == null) {
                email = "null";
            } else {
                email = row.getCell(2).toString();

            }

            usu.setId(Integer.parseInt((id).substring(0, (id).indexOf("."))));
            System.out.println("b");
            usu.setName(name);
            usu.setEmail(email);

               /* ObjectMapper mapper = new ObjectMapper();


                //System.out.println( f.patatar());

                String jsonInString = mapper.writeValueAsString(usu);
                System.out.println(jsonInString);*/
            //System.out.println(usuarioClient.greeting());
            // usuarioClient.json(usu.getName(),usu.getEmail());
            listJson.add(usu);
        }




        file.close();
        return listJson;

    }

}