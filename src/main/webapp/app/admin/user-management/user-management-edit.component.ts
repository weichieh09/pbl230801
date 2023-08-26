import { email, maxLength, minLength, required } from 'vuelidate/lib/validators';
import { Component, Inject, Vue } from 'vue-property-decorator';
import UserManagementService from './user-management.service';
import { IUser, User } from '@/shared/model/user.model';
import AlertService from '@/shared/alert/alert.service';
import axios from 'axios';

const loginValidator = (value: string) => {
  if (!value) {
    return true;
  }
  return /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/.test(value);
};

const validations: any = {
  userAccount: {
    login: {
      required,
      maxLength: maxLength(254),
      pattern: loginValidator,
    },
    firstName: {
      maxLength: maxLength(50),
    },
    lastName: {
      maxLength: maxLength(50),
    },
    email: {
      required,
      email,
      minLength: minLength(5),
      maxLength: maxLength(50),
    },
  },
};

@Component({
  validations,
})
export default class JhiUserManagementEdit extends Vue {
  @Inject('userManagementService') private userManagementService: () => UserManagementService;
  @Inject('alertService') private alertService: () => AlertService;

  public userAccount: IUser;
  public isSaving = false;
  public authorities: any[] = [];
  public languages: any = this.$store.getters.languages;
  public teams: any[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initAuthorities();
      vm.getTeamList();
      if (to.params.userId) {
        vm.init(to.params.userId);
      }
    });
  }

  public constructor() {
    super();
    this.userAccount = new User();
    this.userAccount.authorities = [];
  }

  public initAuthorities() {
    this.userManagementService()
      .retrieveAuthorities()
      .then(_res => {
        this.authorities = _res.data;
        console.log('_res.data: ' + _res.data);
      });
  }

  public init(userId: number): void {
    this.userManagementService()
      .get(userId)
      .then(res => {
        this.userAccount = res.data;
      });
  }

  public getTeamList(): void {
    this.userAccount.wTeamId = null;
    this.teams.push({ text: '請選擇', value: null });
    axios
      .get('/api/wcc101/teams')
      .then(res => {
        res.data.forEach(team => {
          this.teams.push({ text: team.name, value: team.id });
        });
      })
      .catch(error => {
        console.log(error);
      });
  }

  public previousState(): void {
    (<any>this).$router.go(-1);
  }

  public save(): void {
    this.isSaving = true;
    if (this.userAccount.id) {
      this.userManagementService()
        .update(this.userAccount)
        .then(res => {
          this.returnToList();
          (this.$root as any).$bvToast.toast('修改用戶成功', {
            toaster: 'b-toaster-top-center',
            title: '修改成功',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = true;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      if (this.userAccount.authorities.length === 0) {
        this.errorAlert();
        return;
      }
      if (!this.userAccount.authorities.includes('ROLE_ADMIN') && this.userAccount.wTeamId === null) {
        this.errorAlert();
        return;
      }
      this.userAccount.langKey = 'en';
      this.userManagementService()
        .create(this.userAccount)
        .then(res => {
          this.returnToList();
          (this.$root as any).$bvToast.toast('新增用戶成功', {
            toaster: 'b-toaster-top-center',
            title: '新增成功',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = true;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  private returnToList(): void {
    this.isSaving = false;
    (<any>this).$router.go(-1);
  }

  private getMessageFromHeader(res: any): any {
    return res.headers['x-pbl230801app-alert'];
  }

  private errorAlert(): void {
    (this.$root as any).$bvToast.toast('資料不正確', {
      toaster: 'b-toaster-top-center',
      title: '新增失敗',
      variant: 'danger',
      solid: true,
      autoHideDelay: 5000,
    });
  }
}
