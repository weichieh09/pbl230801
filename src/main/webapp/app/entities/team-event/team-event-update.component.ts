import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { ITeamEvent, TeamEvent } from '@/shared/model/team-event.model';
import TeamEventService from './team-event.service';

const validations: any = {
  teamEvent: {
    tId: {},
    eId: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class TeamEventUpdate extends Vue {
  @Inject('teamEventService') private teamEventService: () => TeamEventService;
  @Inject('alertService') private alertService: () => AlertService;

  public teamEvent: ITeamEvent = new TeamEvent();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamEventId) {
        vm.retrieveTeamEvent(to.params.teamEventId);
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
    if (this.teamEvent.id) {
      this.teamEventService()
        .update(this.teamEvent)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TeamEvent is updated with identifier ' + param.id;
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
      this.teamEventService()
        .create(this.teamEvent)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TeamEvent is created with identifier ' + param.id;
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
      this.teamEvent[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.teamEvent[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.teamEvent[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.teamEvent[field] = null;
    }
  }

  public retrieveTeamEvent(teamEventId): void {
    this.teamEventService()
      .find(teamEventId)
      .then(res => {
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.teamEvent = res;
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
