package com.dam.servicios.servicios.almacenamiento;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.dam.servicios.servicios.clases.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FileSystemStorageService implements StorageService {
    private ArrayList<Usuario> listJson;
    private final Path rootLocation;

    private FeignConsumer usuarioClient;
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);

                procesar(file);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public ArrayList<Usuario> procesar(MultipartFile a) throws IOException {

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


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}