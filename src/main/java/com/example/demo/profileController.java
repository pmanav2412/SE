package com.example.demo;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
@Controller
public class profileController {
		
		@GetMapping(value = "/")
		public ModelAndView renderPage() {
			ModelAndView indexPage = new ModelAndView();
			indexPage.setViewName("index");
			return indexPage;
			
			
		}
		
		@PostMapping(value = "/profile")
		public ModelAndView uploadToS3(
				@RequestParam("file") MultipartFile image
				)
		{
			ModelAndView profilePage = new ModelAndView();
			BasicAWSCredentials cred = new BasicAWSCredentials(
					"AKIAINUGJKC4BRTAYHEQ","kUu7z3WgLnpcXOMjF/c08YHlkI6huXR3lWG/D2Pl" 
						);
			
			AmazonS3 s3client = AmazonS3ClientBuilder
					.standard()
					.withCredentials(new AWSStaticCredentialsProvider(cred))
					.withRegion(Regions.US_EAST_1)
					.build();
			
			try {
				PutObjectRequest putReq = new PutObjectRequest("hw2se2",image.getOriginalFilename(),image.getInputStream(),new ObjectMetadata());
				withCannedAcl(CannedAccessControlList.PublicRead);
				s3client.putObject(putReq);
				  String imgsrc =  "http://"+"hw2se2"  +".s3.amazonaws.com/"+image.getOriginalFilename();
		    	   profilePage.addObject("imgsrc",imgsrc);
		    	   profilePage.setViewName("profile");
		    	   return profilePage;
			}
			catch(IOException e)
			{
				e.printStackTrace();
				profilePage.setViewName("error");
				return profilePage;
			}
				
		}

		private void withCannedAcl(CannedAccessControlList publicread) {
			// TODO Auto-generated method stub
			
		}
}
