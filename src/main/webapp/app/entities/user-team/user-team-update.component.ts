import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IUserTeam, UserTeam } from '@/shared/model/user-team.model';
import UserTeamService from './user-team.service';

const validations: any = {
  userTeam: {
    uId: {},
    tId: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class UserTeamUpdate extends Vue {
  @Inject('userTeamService') private userTeamService: () => UserTeamService;
  @Inject('alertService') private alertService: () => AlertService;

  public userTeam: IUserTeam = new UserTeam();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userTeamId) {
        vm.retrieveUserTeam(to.params.userTeamId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.userTeam.id) {
      this.userTeamService()
        .update(this.userTeam)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A UserTeam is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.userTeamService()
        .create(this.userTeam)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A UserTeam is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.userTeam[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.userTeam[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.userTeam[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.userTeam[field] = null;
    }
  }

  public retrieveUserTeam(userTeamId): void {
    this.userTeamService()
      .find(userTeamId)
      .then(res => {
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.userTeam = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
