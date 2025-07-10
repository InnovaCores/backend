package pe.edu.upc.managewise.backend.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.managewise.backend.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.managewise.backend.iam.domain.model.entities.Role;
import pe.edu.upc.managewise.backend.iam.domain.services.RecaptchaService;
import pe.edu.upc.managewise.backend.iam.domain.services.UserCommandService;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.resources.*;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;

import java.util.ArrayList;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

  private final UserCommandService userCommandService;
  private final RecaptchaService recaptchaService;

  public AuthenticationController(UserCommandService userCommandService, RecaptchaService recaptchaService) {
    this.userCommandService = userCommandService;
    this.recaptchaService = recaptchaService;
  }

  private boolean isWebRequest(HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent");
    return userAgent != null && (
            userAgent.contains("Mozilla") ||
                    userAgent.contains("Chrome") ||
                    userAgent.contains("Safari") ||
                    userAgent.contains("Firefox") ||
                    userAgent.contains("Edge") ||
                    userAgent.contains("Opera GX")
    );
  }

  /**
   * Handles the sign-in request.
   * @param signInResource the sign-in request body.
   * @return the authenticated user resource.
   */

  @PostMapping("/sign-in")
  public ResponseEntity<AuthenticatedUserResource> signIn(
          @RequestBody SignInResource signInResource,
          HttpServletRequest request) {

    if (isWebRequest(request)) {
      String token = signInResource.recaptchaToken(); // ✅ token desde el body
      if (token == null || !recaptchaService.verifyRecaptcha(token)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    }

    var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
    var authenticatedUser = userCommandService.handle(signInCommand);
    if (authenticatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
            .toResourceFromEntity(
                    authenticatedUser.get().getLeft(),
                    authenticatedUser.get().getRight()
            );

    return ResponseEntity.ok(authenticatedUserResource);
  }

  @PostMapping("/mobile/sign-in")
  public ResponseEntity<AuthenticatedUserResource> signInMobile(
          @RequestBody SignInMobileResource signInResource,
          HttpServletRequest request) {

    var signInCommand = new SignInCommand(signInResource.username(), signInResource.password());
    var authenticatedUser = userCommandService.handle(signInCommand);
    if (authenticatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
            .toResourceFromEntity(
                    authenticatedUser.get().getLeft(),
                    authenticatedUser.get().getRight()
            );

    return ResponseEntity.ok(authenticatedUserResource);
  }

  /**
   * Handles the sign-up request.
   * @param signUpResource the sign-up request body.
   * @return the created user resource.
   */

  @PostMapping("/sign-up")
  public ResponseEntity<UserResource> signUp(
          @RequestBody SignUpResource signUpResource,
          HttpServletRequest request) {

    System.out.println("🔐 Username: " + signUpResource.username());
    System.out.println("🔐 Password: " + signUpResource.password());
    System.out.println("🔐 Recaptcha token: " + signUpResource.recaptchaToken());

    if (isWebRequest(request)) {
      String token = signUpResource.recaptchaToken(); // ✅ accediendo al campo del record
      if (token == null || !recaptchaService.verifyRecaptcha(token)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    }

    var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
    var user = userCommandService.handle(signUpCommand);
    if (user.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }

  @PostMapping("/mobile/sign-up")
  public ResponseEntity<UserResource> signUpMobile(
          @RequestBody SignUpMobileResource signUpResource,
          HttpServletRequest request) {

    var roles = signUpResource.roles() != null
            ? signUpResource.roles().stream().map(name -> Role.toRoleFromName(name)).toList()
            : new ArrayList<Role>();

    var signUpCommand = new SignUpCommand(signUpResource.username(), signUpResource.password(), roles);
    var user = userCommandService.handle(signUpCommand);
    if (user.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }
}



