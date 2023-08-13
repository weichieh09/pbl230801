import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { ITeam, Team } from '@/shared/model/team.model';
import TeamService from './team.service';

const validations: any = {
  team: {
    teamNm: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class TeamUpdate extends Vue {
  @Inject('teamService') private teamService: () => TeamService;
  @Inject('alertService') private alertService: () => AlertService;

  public team: ITeam = new Team();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamId) {
        vm.retrieveTeam(to.params.teamId);
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
    if (this.team.id) {
      this.teamService()
        .update(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = '球隊更新成功!';
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: '更新成功',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.teamService()
        .create(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = '球隊建立成功!';
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: '建立成功',
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
      this.team[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.team[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.team[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.team[field] = null;
    }
  }

  public retrieveTeam(teamId): void {
    this.teamService()
      .find(teamId)
      .then(res => {
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.team = res;
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
