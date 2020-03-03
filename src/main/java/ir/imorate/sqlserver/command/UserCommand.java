package ir.imorate.sqlserver.command;

import ir.imorate.sqlserver.AAA.entity.User;
import ir.imorate.sqlserver.AAA.service.UserService;
import ir.imorate.sqlserver.shell.InputReader;
import ir.imorate.sqlserver.shell.ShellHelper;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.util.StringUtils;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private final UserService userService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;

    @ShellMethod("Create new user")
    public void create(@ShellOption({"-u", "--username"}) String userName) {
        if (userService.existsByUsername(userName)) {
            shellHelper.printError("Username exists. Enter another username");
            return;
        }
        User user = new User();
        user.setUsername(userName);
        getUserInput(user);
        userService.save(user);
        shellHelper.printSuccess("User successfully created.");
        printUserTable(user);
    }

    @ShellMethod("Delete user")
    public void delete(@ShellOption({"-u", "--username"}) String username) {
        Optional<User> user = userService.findByUsername(username);
        user.ifPresent(userService::delete);
    }

    @ShellMethod("Update user")
    public void update(@ShellOption({"-u", "--username"}) String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            getUserInput(user.get());
            userService.save(user.get());
            printUserTable(user.get());
        }
    }

    @ShellMethod("Get a user information")
    public void get(@ShellOption({"-u", "--username"}) String username) {
        Optional<User> user = userService.findByUsername(username);
        user.ifPresent(this::printUserTable);
    }

    private void getUserInput(User user) {
        do {
            String firstName = inputReader.prompt("First name");
            if (StringUtils.hasText(firstName)) {
                user.setFirstName(firstName);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        } while (user.getFirstName() == null);
        do {
            String lastName = inputReader.prompt("Last name");
            if (StringUtils.hasText(lastName)) {
                user.setLastName(lastName);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        } while (user.getLastName() == null);
        do {
            String password = inputReader.prompt("Password");
            if (StringUtils.hasText(password)) {
                user.setPassword(password);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        } while (user.getPassword() == null);
        do {
            String email = inputReader.prompt("Email");
            if (StringUtils.hasText(email)) {
                user.setEmail(email);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        } while (user.getEmail() == null);
    }

    private void printUserTable(User user) {
        Object[] COLUMNS = {"Username", " First name", "Last name", "Email", "Password", "Last IP address"};
        Object[] ROW = {user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
                user.getLastIP()};
        Object[][] tableData = new Object[][]{COLUMNS, ROW};
        TableModel model = new ArrayTableModel(tableData);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(200));
    }
}
