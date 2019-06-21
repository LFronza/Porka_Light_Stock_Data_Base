/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LoteService } from 'app/entities/lote/lote.service';
import { ILote, Lote } from 'app/shared/model/lote.model';

describe('Service Tests', () => {
    describe('Lote Service', () => {
        let injector: TestBed;
        let service: LoteService;
        let httpMock: HttpTestingController;
        let elemDefault: ILote;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LoteService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Lote(0, currentDate, 0, currentDate, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dtEntrada: currentDate.format(DATE_FORMAT),
                        dtVencimento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Lote', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dtEntrada: currentDate.format(DATE_FORMAT),
                        dtVencimento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dtEntrada: currentDate,
                        dtVencimento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Lote(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Lote', async () => {
                const returnedFromService = Object.assign(
                    {
                        dtEntrada: currentDate.format(DATE_FORMAT),
                        qtEntrada: 1,
                        dtVencimento: currentDate.format(DATE_FORMAT),
                        nrLote: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dtEntrada: currentDate,
                        dtVencimento: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Lote', async () => {
                const returnedFromService = Object.assign(
                    {
                        dtEntrada: currentDate.format(DATE_FORMAT),
                        qtEntrada: 1,
                        dtVencimento: currentDate.format(DATE_FORMAT),
                        nrLote: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dtEntrada: currentDate,
                        dtVencimento: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Lote', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
