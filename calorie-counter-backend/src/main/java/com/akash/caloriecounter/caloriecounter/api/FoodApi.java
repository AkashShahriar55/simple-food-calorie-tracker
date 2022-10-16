package com.akash.caloriecounter.caloriecounter.api;


import com.akash.caloriecounter.models.ResponseMessage;
import com.akash.caloriecounter.services.FileStorageService;
import com.akash.caloriecounter.user.api.User;
import com.akash.caloriecounter.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/foods")
public class FoodApi {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileStorageService storageService;


    @DeleteMapping("/delete")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ResponseMessage> delete(
            @RequestBody Food food
    ){
        foodRepository.delete(food);
        return ResponseEntity.ok().body(new ResponseMessage("deleted"));
    }

    @PutMapping("/update")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ResponseMessage> update(
            @RequestBody Food food
    ){
        foodRepository.save(food);
        return ResponseEntity.ok().body(new ResponseMessage("deleted"));
    }

    @GetMapping("/all")
    @RolesAllowed({"ROLE_ADMIN"})
    public List<Food> allList(
            @RequestParam("item_per_page") int itemPerPage,
            @RequestParam("page_no") int pageNo
            ){

        Pageable pageable = PageRequest.of(pageNo,itemPerPage);



        return foodRepository.findAll(pageable).toList();
    }


    @GetMapping("/report")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<?> getReports(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,-6);
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        Date lastWeekEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,-6);
        Date lastWeekStart = calendar.getTime();
        int thisWeekCount = foodRepository.countEntriesOfDays(weekStart,today);
        int lastWeekCount = foodRepository.countEntriesOfDays(lastWeekStart,lastWeekEnd);
        List<OnlyAvgData> avgCalorieList = foodRepository.getAvgCalories();
        List<UsersAvgCalorie> list = new ArrayList<>();
        for(OnlyAvgData data : avgCalorieList){
            list.add(new UsersAvgCalorie(data.getName(),data.getAvg()));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String test = simpleDateFormat.format(today) + " " + simpleDateFormat.format(weekStart)
                + " " + simpleDateFormat.format(lastWeekStart) +" " + simpleDateFormat.format(lastWeekEnd);
        return ResponseEntity.ok().body(new ReportResponse(thisWeekCount,lastWeekCount,test,list));
    }



    @GetMapping
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<?> list(@RequestParam("from_date") String fromDate,@RequestParam("to_date") String toDate) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
        List<Food> foods = foodRepository.findWithDateFilter(user,from,to);
        Float calorieLimit = userRepository.findById(user.getId()).get().getCalorieLimit();

        return ResponseEntity.ok().body(new ClientFoodResponse(calorieLimit,foods)) ;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Food> create(
            @RequestPart("food_data") @Valid  Food food  ,
            @RequestPart("image") MultipartFile multipartFile
    ) {

        System.out.println(food);
        System.out.println(multipartFile.isEmpty());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        food.setUser((User) authentication.getPrincipal());

        if(!multipartFile.isEmpty()){
            try {
                String fileName = "food_image_"+System.currentTimeMillis()+".png";
                storageService.save(multipartFile,fileName);
                food.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Food savedFood = foodRepository.save(food);
        URI productURI = URI.create("/foods/" + savedFood.getId());
        return ResponseEntity.created(productURI).body(savedFood);
    }

//    @PostMapping
//    public ResponseEntity<Food> create(@RequestBody @Valid  Food food ) throws IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        food.setUser((User) authentication.getPrincipal());
//        Food savedFood = foodRepository.save(food);
//        URI productURI = URI.create("/foods/" + savedFood.getId());
//        return ResponseEntity.created(productURI).body(savedFood);
//    }




}
