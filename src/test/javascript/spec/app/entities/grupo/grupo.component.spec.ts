/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { GrupoComponent } from 'app/entities/grupo/grupo.component';
import { GrupoService } from 'app/entities/grupo/grupo.service';
import { Grupo } from 'app/shared/model/grupo.model';

describe('Component Tests', () => {
    describe('Grupo Management Component', () => {
        let comp: GrupoComponent;
        let fixture: ComponentFixture<GrupoComponent>;
        let service: GrupoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [GrupoComponent],
                providers: []
            })
                .overrideTemplate(GrupoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrupoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Grupo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.grupos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
