package com.project.RTRT.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "table")
public class TischController {
    @Autowired
    TischRepository tischRepository;
    @GetMapping("findall")
    public List<Tisch> getTable(){
        // get all table
        return tischRepository.findAll();
    }

    @PostMapping("add")
    public ResponseEntity<Tisch> addTable(@RequestBody Tisch table){
        // add a new table
        return new ResponseEntity<>(tischRepository.save(table), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    // Delete table
    public ResponseEntity<Tisch> deleteTable(@PathVariable Integer id){
        if (this.tischRepository.findById(id).isPresent()){
            tischRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    

}
