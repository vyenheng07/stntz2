#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'AoERuntime'
  s.version          = '1.1.0'
  s.summary          = 'AoE runtime pods'
  s.description      = <<-DESC
        this pod is a demo for Biz
                       DESC
  s.homepage         = 'https://github.com/didi/aoe/'
  s.license          = { :type => 'Apache', :file => 'LICENSE' }
  s.author           = { 'dingc' => 'dc328466990@163.com' }  
  s.source           = { :git => "git@github.com:didi/aoe.git.git", :tag => s.version.to_s }

  s.ios.deployment_target = '8.0'
  s.prefix_header_file = false
  s.default_subspec = 'Core'


  s.subspec 'Core' do |ss|
    ss.ios.deployment_target = '8.0'
    ss.source_files = "core/Classes/**/*"
    ss.public_header_files = "core/Classes/**/*.h"
    ss.frameworks = 'UIKit','CoreGraphics','Foundation'
    ss.dependency 'AoE'
    ss.resource_bundles = {
      'AoERuntime-Core' => ["core/Assets/**/*"]
    }
  end

  s.subspec 'NCNN' do |ss|
    ss.ios.deployment_target = '8.0'
    ss.source_files = "ncnn/Classes/**/*"
    ss.public_header_files = "ncnn/Classes/**/*.h"
    ss.frameworks = 'CoreImage','UIKit','CoreGraphics','Foundation'
    ss.vendored_frameworks = "ncnn/Frameworks/ncnn.framework","ncnn/Frameworks/openmp.framework"
    ss.dependency "#{s.name}/Core"
    ss.resource_bundles = {
      'AoERuntime-NCNN' => ["ncnn/Assets/**/*"]
    }
  end

  s.subspec 'TensorFlowLite' do |ss|
    ss.ios.deployment_target = '8.0'
    ss.source_files = "tensorFlowLite/Classes/**/*"
    ss.public_header_files = "tensorFlowLite/Classes/**/*.h"
    ss.dependency "#{s.name}/Core"
    ss.dependency "TensorFlowLiteObjC"
    ss.frameworks = 'CoreVideo','UIKit','CoreGraphics','Foundation'
    ss.libraries = ['c++']
    ss.resource_bundles = {
      'AoERuntime-tensorFlowLite' => ["tensorFlowLite/Assets/**/*"]
    }
  end

  s.subspec 'MNN' do |ss|
    ss.ios.deployment_target = '8.0'
    ss.source_files = "mnn/Classes/**/*"
    ss.public_header_files = "mnn/Classes/**/*.h"
    ss.dependency "#{s.name}/Core"
    ss.vendored_frameworks = "mnn/Frameworks/MNN.framework"
    ss.frameworks = 'CoreVideo','UIKit','CoreGraphics','Foundation'
    ss.libraries = ['c++']
    ss.resource_bundles = {
      'AoERuntime-mnn' => ["mnn/Assets/**/*"]
    }
    ss.resources = ["mnn/Frameworks/MNN.framework/mnn.metallib"]
    # ss.script_phase = { :name => 'copy metallibs files', :shell_path => '/bin/bash', :script => 'ruby ../HTWMLModels/installMLfiles.ruby', :execution_position => :after_compile }
  end

end
