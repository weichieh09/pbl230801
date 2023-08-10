import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IEventZ, EventZ } from '@/shared/model/event-z.model';
import EventZService from './event-z.service';

const validations: any = {
  eventZ: {
    evntNm: {},
    evntDt: {},
    venue: {},
    eventBegTime: {},
    eventEndTime: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class EventZUpdate extends Vue {
  @Inject('eventZService') private eventZService: () => EventZService;
  @Inject('alertService') private alertService: () => AlertService;

  public eventZ: IEventZ = new EventZ();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eventZId) {
        vm.retrieveEventZ(to.params.eventZId);
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
    if (this.eventZ.id) {
      this.eventZService()
        .update(this.eventZ)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A EventZ is updated with identifier ' + param.id;
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
      this.eventZService()
        .create(this.eventZ)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A EventZ is created with identifier ' + param.id;
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
      this.eventZ[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.eventZ[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.eventZ[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.eventZ[field] = null;
    }
  }

  public retrieveEventZ(eventZId): void {
    this.eventZService()
      .find(eventZId)
      .then(res => {
        res.evntDt = new Date(res.evntDt);
        res.eventBegTime = new Date(res.eventBegTime);
        res.eventEndTime = new Date(res.eventEndTime);
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.eventZ = res;
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
