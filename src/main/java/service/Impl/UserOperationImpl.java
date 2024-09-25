/*
package service.Impl;

import entity.Users;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UserOperationImpl<T extends Users> */
/*implements UserOperation *//*
{
 */
/*   private final UserGateway<T> userGateway;
   // private final Validator validator;

   // @Override
    public void changePassword(ChangePasswordDto passwordDto) {
        if (isNotValid(passwordDto)) return;
        T user = (T) userGateway.findById(passwordDto.userId());
        if (user != null) {
            user.setPassword(hashPassword(passwordDto.password()));
            userGateway.update(user);
        } else System.err.println("user not found");
    }
    private boolean isNotValid(ChangePasswordDto passwordDto) {
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(passwordDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<ChangePasswordDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return true;
        }
        return false;
    }
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }*//*

}
*/
