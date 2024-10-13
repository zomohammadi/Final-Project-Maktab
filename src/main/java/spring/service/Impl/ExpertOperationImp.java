package spring.service.Impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import spring.dto.*;
import spring.entity.Credit;
import spring.entity.Expert;
import spring.enumaration.Role;
import spring.enumaration.Status;
import spring.mapper.Mapper;
import spring.repository.ExpertGateway;
import spring.service.ExpertOperation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpertOperationImp implements ExpertOperation {
    private final ExpertGateway expertGateway;

    @Override
    @Transactional
    public void register(RegisterExpertDto expertDto) {
        try {
            // Validate the image file
            validateImageFile(expertDto.pictureFile());

            // Check if the input already exists in the database
            checkInputIsExistsInDB(expertDto);

            // Convert DTO to entity and process the image
            Expert expert = Mapper.ConvertDtoToEntity.convertExpertDtoToEntity(expertDto);
            expert.setRole(Role.Expert);
            expert.setCredit(Credit.builder().build());

            // Process the image file and store it (assuming byte array for now)
            expert.setPicture(processImageFile(expertDto.pictureFile().getInputStream()));

            // Hash the password and save the expert
            expert.setPassword(hashPassword(expertDto.password()));
            expertGateway.save(expert);

        } catch (IOException e) {
            throw new IllegalArgumentException("Error processing image file", e);
        }
    }

    private byte[] processImageFile(InputStream imageInputStream) throws IOException {
        return imageInputStream.readAllBytes(); // This converts the InputStream into a byte array
    }

    private void validateImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        // Check file size (300 KB = 307,200 bytes)
        long fileSizeInBytes = file.getSize();
        long maxFileSize = 307200; // 300 KB in bytes
        if (fileSizeInBytes > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds the 300 KB limit");
        }
        // convert to InputStream
        InputStream inputStream = file.getInputStream();

        // validate that it's an image
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null) {
            throw new IllegalArgumentException("Uploaded file is not a valid image");
        }

        // Check the format (JPEG/JPG)
        String fileType = file.getContentType();
        if (!"image/jpeg".equalsIgnoreCase(fileType)) {
            throw new IllegalArgumentException("Only JPG/JPEG images are allowed");
        }
    }

    private void checkInputIsExistsInDB(RegisterExpertDto expertDto) {

        Set<String> errors = CheckInputFromDBOperation.checkUserInfoFromDB("Expert",
                expertGateway.existUserByNationalCode(expertDto.nationalCode()),
                expertGateway.existUserByMobileNumber(expertDto.mobileNumber()),
                expertGateway.existUserByEmailAddress(expertDto.emailAddress()),
                expertGateway.existUserByUserName(expertDto.userName()));
        if (!errors.isEmpty())
            throw new EntityExistsException(String.join(", ", errors));

    }


    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    @Transactional
    public void update(ChangeExpertDto expertDto) {
        validateExpertDataForUpdate(expertDto);
        Expert expert = findById(expertDto.expertId());
        updateOperation(expertDto, expert);
    }

    private void validateExpertDataForUpdate(ChangeExpertDto expertDto) {
        Set<String> errors = new HashSet<>();

        Map<String, Boolean> validationMap = new HashMap<>();
        validationMap.put("Mobile Number", expertDto.mobileNumber() != null
                                           && expertGateway.existUserByMobileNumber(expertDto.mobileNumber()));
        validationMap.put("National Code", expertDto.nationalCode() != null
                                           && expertGateway.existUserByNationalCode(expertDto.nationalCode()));
        validationMap.put("Email Address", expertDto.emailAddress() != null
                                           && expertGateway.existUserByEmailAddress(expertDto.emailAddress()));
        validationMap.put("Username", expertDto.userName() != null
                                      && expertGateway.existUserByUserName(expertDto.userName()));

        validationMap.forEach((fieldName, exists) -> {
            if (exists) {
                errors.add("Expert with this " + fieldName + " already exists");
            }
        });

        if (!errors.isEmpty()) {
            throw new EntityExistsException(String.join(", ", errors));
        }
    }

    private void updateOperation(ChangeExpertDto expertDto, Expert expert) {

        applyIfNotNull(expertDto.firstName(), expert::setFirstName);
        applyIfNotNull(expertDto.lastName(), expert::setLastName);
        applyIfNotNull(expertDto.mobileNumber(), expert::setMobileNumber);
        applyIfNotNull(expertDto.nationalCode(), expert::setNationalCode);
        applyIfNotNull(expertDto.emailAddress(), expert::setEmailAddress);
        applyIfNotNull(expertDto.userName(), expert::setUserName);
        expertGateway.save(expert);
    }

    private <T> void applyIfNotNull(T value, Consumer<T> setterMethod) {
        if (value != null) {
            setterMethod.accept(value);
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto passwordDto) {
        Expert expert = expertGateway.findById(passwordDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Expert Not Found"));
        if (BCrypt.checkpw(passwordDto.oldPassword(), expert.getPassword())) {
            expert.setPassword(hashPassword(passwordDto.password()));
            expertGateway.save(expert);
        } else
            throw new IllegalArgumentException("Old password does not match");
    }


    @Override
    public Expert findById(Long expertId) {
        if (expertId == null)
            throw new IllegalArgumentException("expertId can not be Null");
        return expertGateway.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Expert Not Found"));

    }//ResponceExpertDto

    @Override
    public void confirmedExpert(Long expertId) {
        if (expertId == null)
            throw new IllegalArgumentException("expertId can not be Null");
        Expert expert = expertGateway.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Expert Not Found"));
        if (expert.getStatus().equals(Status.NEW))
            expert.setStatus(Status.CONFIRMED);
    }

@Override
//@Transactional(readOnly = true)
public File getPictureFileByUserName(Long expertId) {

    byte[] pictureBytes = expertGateway.getPictureByUserName(expertId);
    if (pictureBytes == null) {
        throw new EntityNotFoundException("Picture for expertId  not found.");
    }

    File imageFile = convertBytesToFile(pictureBytes, expertId);
    return imageFile;
}

    private File convertBytesToFile(byte[] imageBytes, Long expertId) {
        String fileName = expertId + ".jpg"; // File name format
        File outputFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        // Store in temp directory

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(imageBytes); // Write byte[] to file
        } catch (IOException e) {
            throw new RuntimeException("Error while converting byte array to image file", e);
        }

        return outputFile;
    }

}

/*
 @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void getPicture(String userName) {
        byte[] pictureOptional = getPictureByUserName(userName);
        if (pictureOptional == null) {
            throw new NotFoundException("Expert with this User Name not found!");
        }
        convertBytesToFile(pictureOptional, userName);
    }
 private static void convertBytesToFile(byte[] imageBytes, String userName) {
        String outputFilePath = "D:\\Java\\java_inteligent_idea_excercise" +
                                "\\FinalProjectMaktab\\src\\main\\resources\\ExpertPictures\\" + userName + ".jpg";
        File outputFile = new File(outputFilePath);
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            out.write(imageBytes);
            //System.out.println("Image saved successfully at " + outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file", e);
        }
    }
   @Transactional(readOnly = true)
    public byte[] getPictureByUserName(String userName) {
        return expertGateway.getPictureByUserName(userName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void getPicture(String userName) {
        byte[] pictureOptional = getPictureByUserName(userName);
        if (pictureOptional == null) {
            throw new NotFoundException("Expert with this User Name not found!");
        }
        convertBytesToFile(pictureOptional, userName);
    }

    // Method to check if the file size is less than or equal to 300 KB
    private boolean isFileSizeValid(String filePath) {
        File file = new File(filePath);
        return file.length() <= 300 * 1024;
    }

    // Method to convert the JPG file to a byte array
    private byte[] convertFileToBytes(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            return fileContent;
        } catch (FileNotFoundException e) {
            throw new NotFoundException("file not found");
        } catch (IOException e) {
            throw new IoCustomException("Error saving image file", e);
        }
    }

    public static boolean isImageFileUsingProbeContentType(String inputFilePath) {
        File file = new File(inputFilePath);
        Path filePath = file.toPath();
        String mimeType;
        try {
            mimeType = Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new IoCustomException("Error saving image file", e);
        }
        return mimeType != null && (mimeType.equals("image/jpg") || mimeType.equals("image/jpeg"));
    }

    // Method to validate the file and convert it to bytes
    private byte[] processImageFile(String filePath) {
        if (!isImageFileUsingProbeContentType(filePath)) {
            throw new IllegalArgumentException("File is not a valid JPG format.");
        }
        if (!isFileSizeValid(filePath)) {
            throw new IllegalArgumentException("File size exceeds 300 KB.");
        }
        return convertFileToBytes(filePath);
    }

    private static void convertBytesToFile(byte[] imageBytes, String userName) {
        String outputFilePath = "D:\\Java\\java_inteligent_idea_excercise" +
                                "\\FinalProjectMaktab\\src\\main\\resources\\ExpertPictures\\" + userName + ".jpg";
        File outputFile = new File(outputFilePath);
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            out.write(imageBytes);
            System.out.println("Image saved successfully at " + outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file", e);
        }
    }
 */