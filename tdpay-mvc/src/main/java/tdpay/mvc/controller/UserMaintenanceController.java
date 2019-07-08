package tdpay.mvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.isols.common.constants.Flags;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.dto.UserSearchDto;
import tdpay.mvc.dto.UserTableDto;
import tdpay.mvc.entity.Role;
import tdpay.mvc.entity.User;
import tdpay.mvc.form.UserMaintenanceRegistForm;
import tdpay.mvc.form.UserMaintenanceSearchForm;
import tdpay.mvc.service.UserManageService;

/**
 * ユーザー管理画面 コントローラークラス
 */
@Controller
@RequestMapping(value = UrlConstants.USER_MAINTENANCE)
public class UserMaintenanceController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(UserMaintenanceController.class);

    @Autowired
    protected UserManageService userManageService;

    @Autowired
    protected ModelMapper modelMapper;

    @RequestMapping(value = { "", UrlConstants.INDEX }, method = RequestMethod.GET)
    public String index(final Model model) {

		model.addAttribute("userMaintenanceSearchForm", new UserMaintenanceSearchForm());

        return UrlConstants.USER_MAINTENANCE + UrlConstants.INDEX;
    }

	@RequestMapping(value = UrlConstants.SEARCH, method = RequestMethod.POST)
    public String search(@Validated @ModelAttribute UserMaintenanceSearchForm form, final BindingResult bindingResult, final Model model) {
        logger.debug("UserMaintenanceSearchForm = {}", form);
        logger.debug("bindingResult = {}", bindingResult);

        final UserSearchDto userSearchDto = modelMapper.map(form, UserSearchDto.class);
		final List<User> userList = userManageService.getUserList(userSearchDto);
		final List<Role> roleList = userManageService.getRoleList();

		List<UserTableDto> userTableDtoList =
            userList.stream().map(user -> {
                final Role role = roleList.stream().filter(x -> x.getId().equals(user.getRoleId())).findFirst().orElse(null);
                UserTableDto userTableDto = new UserTableDto();
    			userTableDto = modelMapper.map(user, UserTableDto.class);
                userTableDto.setUserId(user.getId());
                userTableDto.setUserName(user.getName());
                if (role != null) {
                    userTableDto.setRoleName(role.getName());
                }
				return userTableDto;
			}).collect(Collectors.toList());

		model.addAttribute("userMaintenanceSearchForm", form);
        logger.debug("userTableDtoList.size() = " + userTableDtoList.size());
		model.addAttribute("userTableDtoList", userTableDtoList);

        return UrlConstants.USER_MAINTENANCE + UrlConstants.INDEX;
    }

    @RequestMapping(value = UrlConstants.ADD, method = RequestMethod.GET)
    public String add(final Model model) {

        model.addAttribute("roleList", userManageService.getRoleList());
		model.addAttribute("userMaintenanceRegistForm", new UserMaintenanceRegistForm());

        return UrlConstants.USER_MAINTENANCE + UrlConstants.REGIST;
    }

    @RequestMapping(value = UrlConstants.EDIT, method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true) String paramUserId, final Model model) {

        final Long userId = Long.valueOf(StringUtils.decrypt(paramUserId));
        final User user = userManageService.getUser(userId);

        final UserMaintenanceRegistForm userMaintenanceRegistForm = modelMapper.map(user, UserMaintenanceRegistForm.class);
        userMaintenanceRegistForm.setUserId(StringUtils.encrypt(Long.toString(user.getId())));
        userMaintenanceRegistForm.setMid(user.getMid());
        userMaintenanceRegistForm.setUserName(user.getName());
        userMaintenanceRegistForm.setLoginId(user.getLoginId());
        userMaintenanceRegistForm.setPassword(null);
        userMaintenanceRegistForm.setPrePassword(user.getPassword());
        userMaintenanceRegistForm.setRoleId(StringUtils.encrypt(user.getRoleId().toString()));

        model.addAttribute("roleList", userManageService.getRoleList());
		model.addAttribute("userMaintenanceRegistForm", userMaintenanceRegistForm);

        return UrlConstants.USER_MAINTENANCE + UrlConstants.REGIST;
    }

    @RequestMapping(value = UrlConstants.DELETE, method = RequestMethod.GET)
    public String delete(final Model model) {

        return UrlConstants.USER_MAINTENANCE + UrlConstants.REGIST;
    }

	@RequestMapping(value = UrlConstants.REGIST, method = RequestMethod.POST)
    public String regist(@Validated @ModelAttribute UserMaintenanceRegistForm form, final BindingResult bindingResult, final Model model) {

        model.addAttribute("roleList", userManageService.getRoleList());

        if (bindingResult.hasErrors()) {
            return UrlConstants.USER_MAINTENANCE + UrlConstants.REGIST;
        }

        if (StringUtils.isEmpty(form.getPassword()) && StringUtils.isEmpty(form.getPrePassword())) {
            bindingResult.rejectValue("password", null, messageSource.getMessage("javax.validation.constraints.NotEmpty.message", null, null));
            setErrorMessage(model);
            return UrlConstants.USER_MAINTENANCE + UrlConstants.REGIST;
        }

        Long userId = null;
        if (!StringUtils.isEmpty(form.getUserId())) {
            userId = Long.valueOf(StringUtils.decrypt(form.getUserId()));
        }

        User user = userManageService.getUser(userId);
        if (user == null) {
            user = modelMapper.map(form, User.class);
            user.setId(userId);
        }
        user.setName(form.getUserName());
        user.setRoleId(Long.valueOf(StringUtils.decrypt(form.getRoleId())));
        user.setEnableFlag(Flags.ENABLE);

        logger.debug("user = {}", user);
        userManageService.saveUserMaintenance(user, StringUtils.isBlank(form.getPassword()) ? null : form.getPassword());

        return UrlConstants.USER_MAINTENANCE + UrlConstants.CONFIRM;
    }

	@RequestMapping(value = UrlConstants.CONFIRM, method = RequestMethod.GET)
    public String confirm(@Validated @ModelAttribute UserMaintenanceRegistForm form, final BindingResult bindingResult, final Model model) {

        model.addAttribute("userMaintenanceRegistForm", new UserMaintenanceRegistForm());

        /* 確認OK と 確認NG で処理を分岐させる */

        return UrlConstants.COMPANY + UrlConstants.REGIST;
    }

}
