package com.epam.training.student_justinas_skierus.cleancode.aircompany.plains;

import com.epam.training.student_justinas_skierus.cleancode.aircompany.models.MilitaryType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class MilitaryPlane extends Plane {

  private final MilitaryType type;

}
