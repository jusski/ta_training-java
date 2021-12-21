package com.epam.training.student_justinas_skierus.cleancode.aircompany.planes;

import com.epam.training.student_justinas_skierus.cleancode.aircompany.exceptions.NotImplementedException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class MyPlane extends Plane {
  public MyPlane() throws NotImplementedException {
    super(null);
    throw new NotImplementedException("MyPlane is still in design stage");
  }



}
