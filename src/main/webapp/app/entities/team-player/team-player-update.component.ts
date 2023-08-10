import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { ITeamPlayer, TeamPlayer } from '@/shared/model/team-player.model';
import TeamPlayerService from './team-player.service';

const validations: any = {
  teamPlayer: {
    tId: {},
    pId: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class TeamPlayerUpdate extends Vue {
  @Inject('teamPlayerService') private teamPlayerService: () => TeamPlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public teamPlayer: ITeamPlayer = new TeamPlayer();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamPlayerId) {
        vm.retrieveTeamPlayer(to.params.teamPlayerId);
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
    if (this.teamPlayer.id) {
      this.teamPlayerService()
        .update(this.teamPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TeamPlayer is updated with identifier ' + param.id;
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
      this.teamPlayerService()
        .create(this.teamPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TeamPlayer is created with identifier ' + param.id;
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
      this.teamPlayer[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.teamPlayer[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.teamPlayer[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.teamPlayer[field] = null;
    }
  }

  public retrieveTeamPlayer(teamPlayerId): void {
    this.teamPlayerService()
      .find(teamPlayerId)
      .then(res => {
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.teamPlayer = res;
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
